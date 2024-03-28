package ru.nsu.ccfit.lisitsin.server;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

import java.io.Closeable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;

@Slf4j
public class Connection extends Thread implements Closeable {

    private final UUID id;

    private final Socket socket;

    private final Server server;

    private final ObjectInputStream in;

    private final ObjectOutputStream out;

    private boolean isStarted = true;

    @SneakyThrows
    public Connection(UUID id, Socket socket, Server server) {
        this.id = id;
        this.socket = socket;
        this.server = server;
        this.in = new ObjectInputStream(socket.getInputStream());
        this.out = new ObjectOutputStream(socket.getOutputStream());
        start();
    }

    @Override
    public synchronized void start() {
        log.info("[CONNECTION] :: Starting connection with id {}.", id);
        super.start();
    }

    @SneakyThrows
    @Override
    public void run() {
            while (isStarted) {
                Object request = in.readObject();
                if (request instanceof BaseDto dto) {
                    dto.setAuthorId(id);
                    server.receive(dto);
                } else {
                    log.info("[CONNECTION] :: Received object isn't a DTO!");
                }
            }
    }

    @SneakyThrows
    public void send(BaseDto dto) {
        out.writeObject(dto);
        out.flush();
    }

    @Override
    @SneakyThrows
    public void close() {
        isStarted = false;
        socket.close();
        in.close();
        out.close();
        this.interrupt();
    }
}
