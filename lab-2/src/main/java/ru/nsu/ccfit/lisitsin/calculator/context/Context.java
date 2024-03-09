package ru.nsu.ccfit.lisitsin.calculator.context;

import java.io.OutputStream;

public interface Context {

    void define(String variable, Double value);

    void pushVariable(String key);

    void pushValue(Double value);

    Double pop();

    Double peek();

    OutputStream getOutputStream();

    void close();

}
