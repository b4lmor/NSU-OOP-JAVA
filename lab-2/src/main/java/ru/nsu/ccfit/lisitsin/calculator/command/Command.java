package ru.nsu.ccfit.lisitsin.calculator.command;

import ru.nsu.ccfit.lisitsin.calculator.context.Context;

public interface Command {

    void execute(Context context, String... args);

    String getErrorMessage();

}
