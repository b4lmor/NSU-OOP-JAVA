package ru.nsu.ccfit.lisitsin.client;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.ClientPropertiesConfiguration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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

    private final BufferedWriter out;

    private boolean isStarted = false;

    @Autowired
    public Client(ClientPropertiesConfiguration configuration) {
        try {
            this.clientSocket = new Socket(configuration.host(), configuration.port());
            this.reader = new BufferedReader(new InputStreamReader(System.in));
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
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
                    String word = reader.readLine();

                    if (!EXIT_COMMAND.equals(word)) {
                        log.info("[Client] :: Sending message ...");
                        out.write(word + "\n");
                        out.flush();
                        continue;
                    }

                    this.close();
                    log.info("[Client] :: Exit");

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
                    log.error("[CLIENT] :: Can't read message!");
                }
            }
        });

    }

    @SneakyThrows
    public void close() {
        isStarted = false;
        this.clientSocket.close();
        this.in.close();
        this.out.close();
        this.reader.close();
    }
}
