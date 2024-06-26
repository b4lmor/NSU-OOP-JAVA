package ru.nsu.ccfit.lisitsin.server;

import jakarta.annotation.PreDestroy;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.chain.FilterChain;
import ru.nsu.ccfit.lisitsin.configuration.ServerPropertiesConfiguration;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.service.UserService;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.sleep;

@Component
@Log4j2
public class Server implements Closeable {

    private static final String EXIT_COMMAND = "exit";

    private static final long DELAY = 2000;

    private final ServerSocket server;

    private final Deque<BaseDto> requests;

    private final FilterChain filterChain;

    private final UserService userService;

    private final Scanner scanner = new Scanner(System.in);

    private boolean isStarted = false;

    @SneakyThrows
    @Autowired
    public Server(ServerPropertiesConfiguration configuration, FilterChain filterChain, UserService userService) {
        this.server = new ServerSocket(configuration.port());
        this.userService = userService;
        this.requests = new ArrayDeque<>();
        this.filterChain = filterChain;
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        isStarted = true;

        ExecutorService serverRoutine = Executors.newFixedThreadPool(4);

        serverRoutine.submit(() -> {
            while (isStarted) {
                try {
                    log.info("[SERVER] :: Waiting for a new socket ...");
                    Socket socket = server.accept();
                    registerConnection(socket);
                    log.info("[SERVER] :: Socket accepted and saved!");
                } catch (IOException e) {
                    if (isStarted) {
                        log.error("[SERVER] :: Can't accept client's socket!");
                    }
                }
            }
        });

        serverRoutine.submit(() -> {
            while (isStarted) {
                String input = scanner.nextLine();
                if (EXIT_COMMAND.equalsIgnoreCase(input)) {
                    log.info("[SERVER] :: Exiting ...");
                    this.close();
                }
            }
        });

        serverRoutine.submit(() -> {
            while (isStarted) {
                if (!requests.isEmpty()) {
                    var dto = requests.poll();
                    process(dto);
                }
            }
        });

        serverRoutine.submit(() -> {
            while (isStarted) {
                ConnectionManager.values()
                        .stream()
                        .filter(Connection::isClosed)
                        .forEach(connection -> {
                            log.info(
                                    "[SERVER] :: Deleting inactive connection with id {}.",
                                    connection.getId()
                            );
                            userService.disconnect(connection.getConnectionId());
                            ConnectionManager.remove(connection.getConnectionId());
                        });
                try {
                    sleep(DELAY);
                } catch (InterruptedException e) {
                    log.error("[SERVER] :: Can't sleep.");
                }
            }
        });

        serverRoutine.shutdown();
    }

    public void receive(BaseDto request) {
        log.info("[SERVER] :: Received a new dto.");
        requests.add(request);
    }

    public void process(BaseDto request) {
        log.info("[SERVER] :: Processing a request ...");
        filterChain.process(request);
        log.info("[SERVER] :: Processing a request ... Done!");
    }

    @SneakyThrows
    @PreDestroy
    public void close() {
        log.info("[SERVER] :: Exiting ...");
        isStarted = false;
        scanner.close();
        ConnectionManager.values().forEach(Connection::close);
        ConnectionManager.keySet().forEach(userService::disconnect);
        server.close();
        log.info("[SERVER] :: Exiting ... Done!");
    }

    private void registerConnection(Socket socket) {
        UUID id = UUID.randomUUID();
        ConnectionManager.put(id, new Connection(id, socket, this));
    }

}
