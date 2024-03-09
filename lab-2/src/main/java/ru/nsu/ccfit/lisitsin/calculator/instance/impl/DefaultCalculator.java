package ru.nsu.ccfit.lisitsin.calculator.instance.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.calculator.command.factory.CommandFactory;
import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.calculator.context.impl.DefaultContext;
import ru.nsu.ccfit.lisitsin.calculator.instance.Calculator;
import ru.nsu.ccfit.lisitsin.reader.CommandReader;

import java.io.OutputStream;

public class DefaultCalculator implements Calculator {

    private final static Logger LOGGER = LogManager.getLogger(DefaultCalculator.class);

    private final Context context;

    public DefaultCalculator(OutputStream outputStream) {
        this.context = new DefaultContext(outputStream);
    }

    @Override
    public void exec(Command command, String... args) {
        LOGGER.info("executing '{}' with args: [{}].",
                command.getClass().getName(),
                String.join(", ", args));
        command.execute(context, args);
    }

    @Override
    public void execAll(String commandFactoryConfigName, String inputFileName) {
        try (var reader = new CommandReader(inputFileName)) {
            CommandFactory cmdFactory = new CommandFactory(commandFactoryConfigName);
            CommandReader.CommandData command;
            while ((command = reader.readNextCommand()) != null) {
                this.exec(cmdFactory.create(command.commandName()), command.args());
            }
        }
    }

    @Override
    public void execAll(String commandFactoryConfigName) {
        try (var reader = new CommandReader()) {
            CommandFactory cmdFactory = new CommandFactory(commandFactoryConfigName);
            CommandReader.CommandData command;
            while ((command = reader.readNextCommand()) != null) {
                try {
                    this.exec(cmdFactory.create(command.commandName()), command.args());
                } catch (Exception e) {
                    LOGGER.error("{}", e.getMessage() == null ? e : e.getMessage());
                }
            }
        }
    }

    @Override
    public void close() {
        context.close();
    }

}
