package ru.nsu.ccfit.lisitsin.exception.calculator.command;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;

public class IllegalArgsException extends RuntimeException {

    public IllegalArgsException(String message) {
        super("Illegal arguments: " + message + ".");
    }

    public IllegalArgsException(Command command) {
        super(command.getErrorMessage());
    }

}
