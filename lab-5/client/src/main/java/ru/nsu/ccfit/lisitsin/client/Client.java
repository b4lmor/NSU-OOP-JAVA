package ru.nsu.ccfit.lisitsin.client;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.ClientPropertiesConfiguration;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Log4j2
public class Client implements Closeable {

    private static final String EXIT_COMMAND = "exit";

    private final Socket clientSocket;

    private final BufferedReader reader;

    private final BufferedReader in;

    private final ObjectOutputStream out;

    private boolean isStarted = false;

    @Autowired
    public Client(ClientPropertiesConfiguration configuration) {
        try {
            this.clientSocket = new Socket(configuration.host(), configuration.port());
            this.reader = new BufferedReader(new InputStreamReader(System.in));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (Throwable e) {
            log.error("[CLIENT] :: Can't connect to the server!");
            throw new RuntimeException("Can't connect to the server! " + e);
        }
    }

    @EventListener(ContextRefreshedEvent.class)
    public void run() {

        isStarted = true;

        ExecutorService clientRoutine = Executors.newFixedThreadPool(2);

        clientRoutine.submit(() -> {
            while (isStarted) {
                try {
                    String message = reader.readLine();
                    if (EXIT_COMMAND.equalsIgnoreCase(message)) {
                        this.close();
                        log.info("[Client] :: Exit");
                        break;
                    }
                    log.debug("[Client] :: Sending message ...");
                    out.writeObject(MessageRequest.builder().message(message).build());
                    out.flush();
                    log.debug("[Client] :: Sending message ... Done!");

                } catch (IOException e) {
                    log.error("[CLIENT] :: Can't send message!");
                }
            }

            clientRoutine.shutdown();
        });

        clientRoutine.submit(() -> {
            while (isStarted) {
                try {
                    String serverWord = in.readLine();
                    System.out.println(serverWord);
                } catch (IOException e) {
                    if (isStarted) {
                        log.error("[CLIENT] :: Can't read message!");
                    }
                }
            }
        });

    }

    @SneakyThrows
    public void close() {
        isStarted = false;
        clientSocket.close();
        in.close();
        out.close();
        reader.close();
    }
}
