package ru.nsu.ccfit.lisitsin.calculator.instance;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;

public interface Calculator extends AutoCloseable {

    void exec(Command command, String... args);

    void execAll(
            String commandFactoryConfigName,
            String inputFileName
    );

    void execAll(String commandFactoryConfigName);
}
