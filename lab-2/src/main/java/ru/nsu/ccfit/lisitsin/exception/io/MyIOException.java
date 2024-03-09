package ru.nsu.ccfit.lisitsin.exception.io;

public class MyIOException extends RuntimeException {

    public MyIOException(String message) {
        super("[File error]: " + message);
    }

}
