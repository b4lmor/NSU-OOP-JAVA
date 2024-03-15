package ru.nsu.ccfit.lisitsin.server;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Connection extends Thread implements Closeable {

    private final Socket socket;

    private final Server server;

    private final BufferedReader in;

    private final BufferedWriter out;

    @SneakyThrows
    public Connection(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.start();
    }

    @SneakyThrows
    @Override
    public void run() {
            while (true) {
                String word = in.readLine();
                if (word.equals("stop")) { // TODO
                    break;
                }
                for (Connection vr : server.getConnections()) {
                    vr.send("[" + new Date() + "] someone said: " + word);
                }
            }
    }

    @SneakyThrows
    private void send(String msg) {
        out.write(msg + '\n');
        out.flush();
    }

    @Override
    @SneakyThrows
    public void close() {
        socket.close();
        in.close();
        out.close();
    }
}
