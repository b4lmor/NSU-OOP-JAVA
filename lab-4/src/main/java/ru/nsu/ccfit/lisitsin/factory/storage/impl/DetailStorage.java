package ru.nsu.ccfit.lisitsin.factory.storage.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.Detail;
import ru.nsu.ccfit.lisitsin.factory.storage.Storage;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
public class DetailStorage<T extends Detail> implements Storage<T> {

    @Getter
    private final int capacity;

    private final ConcurrentLinkedQueue<T> details = new ConcurrentLinkedQueue<>();
    private final Object emptyMonitor = new Object();
    private final Object fullMonitor = new Object();

    @Override
    @SneakyThrows
    public void push(T detail) {

        synchronized (fullMonitor) {
            while (details.size() == capacity) {
                fullMonitor.wait();
            }
        }

        details.add(detail);

        synchronized (emptyMonitor) {
            emptyMonitor.notify();
        }
    }

    @Override
    @SneakyThrows
    public T poll() {

        synchronized (emptyMonitor) {
            while (details.isEmpty()) {
                emptyMonitor.wait();
            }
        }

        var detail = details.poll();

        synchronized (fullMonitor) {
            fullMonitor.notify();
        }

        return detail;
    }

    @Override
    public synchronized boolean isFull() {
        return details.size() == capacity;
    }

    public int getCurrentSize() {
        return details.size();
    }

}
