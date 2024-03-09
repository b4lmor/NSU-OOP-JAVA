package ru.nsu.ccfit.lisitsin.calculator.command.impl;

import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.exception.calculator.command.IllegalArgsException;

public class PushCommand implements Command {
    @Override
    public void execute(Context context, String... args) {
        if (args.length != 1) {
            throw new IllegalArgsException(this);
        }
        context.pushVariable(args[0]);
    }

    @Override
    public String getErrorMessage() {
        return "Bad arguments, try: {variable name}.";
    }
}
