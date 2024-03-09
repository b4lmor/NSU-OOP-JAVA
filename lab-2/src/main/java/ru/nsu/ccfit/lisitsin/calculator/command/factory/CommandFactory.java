package ru.nsu.ccfit.lisitsin.calculator.command.factory;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.exception.calculator.command.CommandClassNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class CommandFactory {

    private final Properties properties;

    public CommandFactory(String commandsPropertiesPath) {
        try (InputStream inputStream = getClass().getResourceAsStream(commandsPropertiesPath)) {
            properties = new Properties();
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException("Can't open config file.");
        }
    }

    public Command create(String property) {
        String className = properties.getProperty(property);
        try {
            return (Command) Class.forName(className).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new CommandClassNotFoundException(className);
        }
    }

}
