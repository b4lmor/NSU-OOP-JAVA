package ru.nsu.ccfit.lisitsin.calculator.context.impl;

import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.exception.calculator.context.VariableNotFoundException;
import ru.nsu.ccfit.lisitsin.exception.io.CloseException;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class DefaultContext implements Context {

    private final static Logger LOGGER = LogManager.getLogger(DefaultContext.class);

    private final Map<String, Double> variables = new HashMap<>();

    private final Deque<Double> stack = new ArrayDeque<>();

    @Getter
    private final OutputStream outputStream;

    public DefaultContext(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void define(String variable, Double value) {
        LOGGER.info("saving variable '{}' with value {}.", variable, value);
        saveVariable(variable, value);
    }

    @Override
    public void pushVariable(String key) {
        LOGGER.info("pushing variable '{}' to stack.", key);
        stack.push(loadValueFromVariable(key));
    }

    @Override
    public void pushValue(Double value) {
        LOGGER.info("pushing value {} to stack.", value);
        stack.push(value);
    }

    @Override
    public Double pop() {
        LOGGER.info("popping value from stack.");
        return stack.pop();
    }

    @Override
    public Double peek() {
        LOGGER.info("peeking value from stack.");
        return stack.peek();
    }

    private void saveVariable(String key, Double value) {
        variables.put(key, value);
    }

    private Double loadValueFromVariable(String key) {
        if (!variables.containsKey(key)) {
            throw new VariableNotFoundException(key);
        }
        return variables.get(key);
    }

    @Override
    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            throw new CloseException(e.getMessage());
        }
    }
}
