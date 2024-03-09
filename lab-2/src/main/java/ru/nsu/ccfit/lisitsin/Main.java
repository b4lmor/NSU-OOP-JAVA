package ru.nsu.ccfit.lisitsin;

import ru.nsu.ccfit.lisitsin.calculator.instance.impl.DefaultCalculator;

import java.io.OutputStream;


public class Main {

    private static final OutputStream OUTPUT_STREAM = System.out;
    private static final String CALCULATOR_DEMO_PATH = "calculator/src/main/resources/calculator1.txt";
    private static final String COMMAND_PROPERTIES_PATH = "/commands.properties";

    public static void main(String[] args) {

        try (var calc = new DefaultCalculator(OUTPUT_STREAM)) {
            calc.execAll(COMMAND_PROPERTIES_PATH);
        }
    }

}
