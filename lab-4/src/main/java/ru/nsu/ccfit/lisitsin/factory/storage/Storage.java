package ru.nsu.ccfit.lisitsin.factory.storage;

public interface Storage<T> {

    void push(T t);

    T poll();

    boolean isFull();

}
