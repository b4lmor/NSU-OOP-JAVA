package ru.nsu.ccfit.lisitsin.client;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.ClientPropertiesConfiguration;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.reponse.BaseResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

@Component
@Log4j2
public class Client {

    private final Object lock = new Object();

    private final Socket socket;

    private final BufferedReader consoleIn;

    private final ObjectInputStream in;

    private final ObjectOutputStream out;


    @Autowired
    public Client(ClientPropertiesConfiguration configuration) {
        try {
            this.consoleIn = new BufferedReader(new InputStreamReader(System.in));
            this.socket = new Socket(configuration.host(), configuration.port());
            this.out = new ObjectOutputStream(socket.getOutputStream());
            this.in = new ObjectInputStream(socket.getInputStream());
        } catch (Throwable e) {
            log.error("[CLIENT] :: Can't connect to the server!");
            throw new RuntimeException("Can't connect to the server! " + e);
        }
    }

    public BaseResponse send(BaseDto dto) {
        writeObject(dto);
        return (BaseResponse) readObject();
    }

    @SneakyThrows
    public void close() {
        socket.close();
        consoleIn.close();
    }

    private void writeObject(Object obj) {
        synchronized (lock) {
            try {
                out.writeObject(obj);
                out.flush();
            } catch (IOException e) {
                log.error("[CLIENT] :: Can't write object! {}.", e.getMessage());
            }
        }
    }

    private Object readObject() {
        synchronized (lock) {
            Object obj = null;
            try {
                obj = in.readObject();
            } catch (IOException | ClassNotFoundException e) {
                log.error("[CLIENT] :: Can't read object! {}.", e.getMessage());
            }
            return obj;
        }
    }
}
