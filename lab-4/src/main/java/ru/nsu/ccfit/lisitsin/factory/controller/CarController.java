package ru.nsu.ccfit.lisitsin.factory.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.monitor.CarMonitor;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.CarStorage;
import ru.nsu.ccfit.lisitsin.factory.worker.Worker;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
public class CarController {

    private final CarMonitor carMonitor = CarMonitor.INSTANCE;

    private final CarStorage carStorage;

    private final List<Worker> workers;
    private boolean isStarted = true;

    @SneakyThrows
    public void run() {

        log.info("[CONTROLLER] :: Controller is running ...");

        while (isStarted) {

            synchronized (carMonitor) {
                carMonitor.wait();
            }

            while (!carStorage.isFull()) {
                var busyWorker = workers.stream().filter(w -> !w.isFree()).findFirst().orElse(null);
                if (busyWorker != null) {
                    synchronized (busyWorker) {
                        busyWorker.notify();
                    }
                }

            }

        }

        log.info("[CONTROLLER] :: Controller is done.");

    }

    public void stop() {
        this.isStarted = false;
    }

}
