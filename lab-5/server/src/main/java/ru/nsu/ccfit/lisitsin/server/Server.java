package ru.nsu.ccfit.lisitsin.server;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.ServerPropertiesConfiguration;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Log4j2
public class Server implements Closeable {

    private static final String EXIT_COMMAND = "exit";

    private final ServerSocket server;

    @Getter
    private final List<Connection> connections;

    private boolean isStarted = false;

    @SneakyThrows
    @Autowired
    public Server(ServerPropertiesConfiguration configuration) {
        this.server = new ServerSocket(configuration.port());
        this.connections = new ArrayList<>();
    }

    @SneakyThrows
    @EventListener(ContextRefreshedEvent.class)
    public void run() {
        isStarted = true;

        ExecutorService serverRoutine = Executors.newFixedThreadPool(2);

        serverRoutine.submit(() -> {
            while (isStarted) {
                try {
                    log.info("[SERVER] :: Waiting for a new socket ...");
                    Socket socket = server.accept();
                    log.info("[SERVER] :: Socket accepted!");
                    connections.add(new Connection(socket, this));
                } catch (IOException e) {
                    if (isStarted) {
                        log.error("[SERVER] :: Can't accept client's socket!");
                    }
                }
            }
        });

        serverRoutine.submit(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String input = scanner.nextLine();
                if (EXIT_COMMAND.equalsIgnoreCase(input)) {
                    this.close();
                    break;
                }
            }
            scanner.close();
            serverRoutine.shutdown();
            log.info("[SERVER] :: Exit");
        });
    }

    @SneakyThrows
    public void close() {
        isStarted = false;
        server.close();
        connections.forEach(Connection::close);
    }

}
