package ru.nsu.ccfit.lisitsin.server;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.chain.FilterChain;
import ru.nsu.ccfit.lisitsin.configuration.ServerPropertiesConfiguration;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Log4j2
public class Server implements Closeable {

    private static final String EXIT_COMMAND = "exit";

    private final ServerSocket server;

    @Getter
    private final Map<UUID, Connection> connections;

    private final Deque<BaseDto> requests;

    private final FilterChain filterChain;

    private final Scanner scanner = new Scanner(System.in);

    private boolean isStarted = false;

    @SneakyThrows
    @Autowired
    public Server(ServerPropertiesConfiguration configuration, FilterChain filterChain) {
        this.server = new ServerSocket(configuration.port());
        this.connections = new HashMap<>();
        this.requests = new ArrayDeque<>();
        this.filterChain = filterChain;
    }

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void run() {
        isStarted = true;

        ExecutorService serverRoutine = Executors.newFixedThreadPool(3);

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

        serverRoutine.shutdown();
    }

    public void receive(BaseDto request) {
        log.trace("[SERVER] :: Received a new dto.");
        requests.add(request);
    }

    public void process(BaseDto request) {
        log.trace("[SERVER] :: Processing a request ...");
        filterChain.process(request);
        log.trace("[SERVER] :: Processing a request ... Done!");
    }

    public void answer(BaseDto response) {
        log.trace("[SERVER] :: Answering ...");
        connections.get(response.getAuthorId()).send(response);
        log.trace("[SERVER] :: Answering ... Done!");
    }

    @SneakyThrows
    public void close() {
        log.info("[SERVER] :: Exiting ...");
        isStarted = false;
        scanner.close();
        connections.values().forEach(Connection::close);
        server.close();
        log.info("[SERVER] :: Exiting ... Done!");
    }

    private void registerConnection(Socket socket) {
        UUID id = UUID.randomUUID();
        connections.put(id, new Connection(id, socket, this));
    }

}
