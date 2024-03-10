package ru.nsu.ccfit.lisitsin.factory.storage.impl;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.entity.Car;
import ru.nsu.ccfit.lisitsin.factory.monitor.CarMonitor;
import ru.nsu.ccfit.lisitsin.factory.storage.Storage;

import java.util.concurrent.ConcurrentLinkedQueue;

@RequiredArgsConstructor
@Log4j2
public class CarStorage implements Storage<Car> {

    @Getter
    private final int capacity;

    private final ConcurrentLinkedQueue<Car> cars = new ConcurrentLinkedQueue<>();
    private final Object emptyMonitor = new Object();
    private final Object fullMonitor = new Object();
    private final CarMonitor carMonitor = CarMonitor.INSTANCE;

    @Override
    @SneakyThrows
    public void push(Car car) {

        synchronized (fullMonitor) {
            while (cars.size() == capacity) {
                fullMonitor.wait();
            }
        }

        cars.add(car);

        synchronized (emptyMonitor) {
            emptyMonitor.notify();
        }
    }

    @Override
    @SneakyThrows
    public Car poll() {

        synchronized (carMonitor) {
            log.info("[CAR-STORAGE] :: Notifying controller ...");
            carMonitor.notify();
        }

        synchronized (emptyMonitor) {
            while (cars.isEmpty()) {
                emptyMonitor.wait();
            }
        }

        var detail = cars.poll();

        synchronized (fullMonitor) {
            fullMonitor.notify();
        }

        return detail;
    }

    @Override
    public synchronized boolean isFull() {
        return cars.size() == capacity;
    }

    public int getCurrentSize() {
        return cars.size();
    }

}
