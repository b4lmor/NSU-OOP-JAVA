package ru.nsu.ccfit.lisitsin.server;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

import java.io.Closeable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Slf4j
public class Connection extends Thread implements Closeable {

    private final Object lock = new Object();

    @Getter
    private final UUID connectionId;

    private final Socket socket;

    private final Server server;

    private final ObjectInputStream in;

    private final ObjectOutputStream out;

    private CountDownLatch latch;

    private boolean isStarted = true;

    @SneakyThrows
    public Connection(UUID connectionId, Socket socket, Server server) {
        this.connectionId = connectionId;
        this.socket = socket;
        this.server = server;
        this.out = new ObjectOutputStream(socket.getOutputStream());
        this.in = new ObjectInputStream(socket.getInputStream());
        start();
    }

    @Override
    public synchronized void start() {
        log.info("[CONNECTION] :: Starting connection with id {}.", connectionId);
        super.start();
    }

    @SneakyThrows
    @Override
    public void run() {
        while (isStarted && !isClosed()) {
            Object request = readObject();
            if (request instanceof BaseDto dto) {
                dto.setAuthorId(connectionId);
                server.receive(dto);
            } else {
                log.info("[CONNECTION] :: Received object isn't a DTO!");
            }
            latch = new CountDownLatch(1);
            latch.await();
        }
        in.close();
    }

    @SneakyThrows
    public void send(BaseDto dto) {
        writeObject(dto);
        latch.countDown();
    }

    public boolean isClosed() {
        return socket.isClosed() || !socket.isConnected();
    }

    @Override
    @SneakyThrows
    public void close() {
        isStarted = false;
        socket.close();
        this.interrupt();
    }

    private void writeObject(Object obj) {
        synchronized (lock) {
            try {
                out.writeObject(obj);
                out.flush();
            } catch (IOException e) {
                log.error("[CONNECTION] :: Can't write object!");
            }
        }
    }

    private Object readObject() {
        synchronized (lock) {
            Object obj = null;
            try {
                obj = in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                log.error("[CONNECTION] :: Can't read object! " + e);
            }
            return obj;
        }
    }
}
