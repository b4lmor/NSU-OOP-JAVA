package ru.nsu.ccfit.lisitsin.exception.calculator.context;

public class VariableNotFoundException extends RuntimeException {
    public VariableNotFoundException(String variable) {
        super("Undefined variable: '" + variable + "'.");
    }
}
