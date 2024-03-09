package ru.nsu.ccfit.lisitsin.calculator.command.impl;

import ru.nsu.ccfit.lisitsin.calculator.context.Context;
import ru.nsu.ccfit.lisitsin.calculator.command.Command;
import ru.nsu.ccfit.lisitsin.exception.calculator.command.IllegalArgsException;

public class DefineCommand implements Command {
    @Override
    public void execute(Context context, String... args) {
        if (args.length != 2) {
            throw new IllegalArgsException(this);
        }
        try {
            context.define(args[0], Double.valueOf(args[1]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgsException("can't cast " + args[1] + " to double");
        }
    }

    @Override
    public String getErrorMessage() {
        return "Bad arguments, try: {variable name} {value}.";
    }
}
