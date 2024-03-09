package ru.nsu.ccfit.lisitsin.exception.calculator.arithmetic;

public class SqrtFromNegativeException extends RuntimeException {
    public SqrtFromNegativeException() {
        super("Can't get sqrt from negative number.");
    }
}
