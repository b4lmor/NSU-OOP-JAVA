package ru.nsu.ccfit.lisitsin.calculator.command.impl;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.exception.calculator.command.IllegalArgsException;

public class MulCommand implements Command {
    @Override
    public void execute(Context context, String... args) {
        if (args.length != 0) {
            throw new IllegalArgsException(this);
        }
        var first = context.pop();
        var second = context.pop();
        context.pushValue(first * second);
    }

    @Override
    public String getErrorMessage() {
        return "This command doesn't have any parameters.";
    }
}
