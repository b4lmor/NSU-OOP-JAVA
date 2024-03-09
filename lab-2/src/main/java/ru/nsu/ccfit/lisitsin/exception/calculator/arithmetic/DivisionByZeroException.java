package ru.nsu.ccfit.lisitsin.exception.calculator.arithmetic;

public class DivisionByZeroException extends RuntimeException {
    public DivisionByZeroException() {
        super("Division by zero.");
    }
}
