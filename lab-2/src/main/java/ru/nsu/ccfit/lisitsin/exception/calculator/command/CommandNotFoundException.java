package ru.nsu.ccfit.lisitsin.exception.calculator.command;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException(String command) {
        super("Unknown command: '" + command + "'.");
    }
}
