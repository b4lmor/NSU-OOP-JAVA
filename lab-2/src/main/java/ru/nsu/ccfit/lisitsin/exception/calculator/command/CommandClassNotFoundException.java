package ru.nsu.ccfit.lisitsin.exception.calculator.command;

public class CommandClassNotFoundException extends RuntimeException {
    public CommandClassNotFoundException(String className) {
        super("Can't find class: '" + className + "'.");
    }
}
