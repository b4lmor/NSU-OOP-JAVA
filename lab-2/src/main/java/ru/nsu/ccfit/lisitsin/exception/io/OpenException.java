package ru.nsu.ccfit.lisitsin.exception.io;

public class OpenException extends MyIOException {

    public OpenException(String filepath) {
        super("can't open file '" + filepath + "'.");
    }


}
