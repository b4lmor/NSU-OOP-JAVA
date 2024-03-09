package ru.nsu.ccfit.lisitsin.reader;

import ru.nsu.ccfit.lisitsin.exception.io.CloseException;
import ru.nsu.ccfit.lisitsin.exception.io.OpenException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class CommandReader implements AutoCloseable {

    private static final String STOP = "STOP";

    private final BufferedReader reader;

    public CommandReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public CommandReader(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            throw new OpenException(e.getMessage());
        }
    }

    public CommandData readNextCommand() {
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                if (line.equals(STOP)) {
                    break;
                }
                var parsedLine = line.split(" ");
                var commandName = parsedLine[0];
                var args = Arrays.copyOfRange(parsedLine, 1, parsedLine.length);
                return new CommandData(commandName, args);
            }
        } catch (IOException e) {
            throw new OpenException(e.getMessage());
        }
        return null;
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new CloseException(e.getMessage());
        }
    }

    public record CommandData(String commandName, String[] args) {
        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof CommandData data) {
                return commandName.equals(data.commandName)
                        && Arrays.equals(args, data.args);
            }
            return false;
        }
    }

}
