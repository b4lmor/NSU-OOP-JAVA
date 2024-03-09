package ru.nsu.ccfit.lisitsin.calculator.command.impl;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.exception.calculator.command.IllegalArgsException;

import java.io.PrintStream;

public class PrintCommand implements Command {
    @Override
    public void execute(Context context, String... args) {
        if (args.length != 0) {
            throw new IllegalArgsException(this);
        }
        new PrintStream(context.getOutputStream()).println(context.peek());
    }

    @Override
    public String getErrorMessage() {
        return "This command doesn't have any parameters.";
    }
}
