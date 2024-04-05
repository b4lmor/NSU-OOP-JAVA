package ru.nsu.ccfit.lisitsin.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.CarFactoryPropertiesConfig;
import ru.nsu.ccfit.lisitsin.factory.controller.CarController;
import ru.nsu.ccfit.lisitsin.factory.dealer.Dealer;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.AccessoryDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.BodyDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.MotorDetail;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.CarStorage;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.DetailStorage;
import ru.nsu.ccfit.lisitsin.factory.supplier.DetailSupplier;
import ru.nsu.ccfit.lisitsin.factory.worker.Worker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Getter
@Log4j2
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarFactory {

    private final CarFactoryPropertiesConfig properties;

    private boolean isStarted;

    private DetailStorage<MotorDetail> motorStorage;

    private DetailStorage<BodyDetail> bodyStorage;

    private DetailStorage<AccessoryDetail> accessoryStorage;

    private CarStorage carStorage;

    private List<DetailSupplier<MotorDetail>> motorSuppliers;

    private List<DetailSupplier<BodyDetail>> bodySuppliers;

    private List<DetailSupplier<AccessoryDetail>> accessorySuppliers;

    private List<Worker> workers;

    private List<Dealer> dealers;

    private CarController carController;

    //@EventListener(ContextRefreshedEvent.class)
    @SneakyThrows
    public void start() {

        isStarted = true;

        motorStorage = new DetailStorage<>(properties.storageMotorSize());
        bodyStorage = new DetailStorage<>(properties.storageBodySize());
        accessoryStorage = new DetailStorage<>(properties.storageAccessorySize());

        carStorage = new CarStorage(properties.storageCarSize());

        motorSuppliers = IntStream.rangeClosed(1, properties.motorSuppliers())
                .mapToObj(i -> new DetailSupplier<>(MotorDetail.class, motorStorage, i))
                .toList();
        bodySuppliers = IntStream.rangeClosed(1, properties.bodySuppliers())
                .mapToObj(i -> new DetailSupplier<>(BodyDetail.class, bodyStorage, i))
                .toList();
        accessorySuppliers = IntStream.rangeClosed(1, properties.accessorySuppliers())
                .mapToObj(i -> new DetailSupplier<>(AccessoryDetail.class, accessoryStorage, i))
                .toList();

        workers = IntStream.rangeClosed(1, properties.workers())
                .mapToObj(i -> new Worker(carStorage, accessoryStorage, bodyStorage, motorStorage, i))
                .toList();

        dealers = IntStream.rangeClosed(1, properties.dealers())
                .mapToObj(i -> new Dealer(carStorage, i))
                .toList();

        carController = new CarController(carStorage, workers);

        ExecutorService carFactoryRoutine = Executors.newSingleThreadExecutor();
        ExecutorService motorSupExecutor = Executors.newFixedThreadPool(properties.motorSuppliers());
        ExecutorService bodySupExecutor = Executors.newFixedThreadPool(properties.bodySuppliers());
        ExecutorService accessorySupExecutor = Executors.newFixedThreadPool(properties.accessorySuppliers());
        ExecutorService workerExecutor = Executors.newFixedThreadPool(properties.workers());
        ExecutorService carControllerExecutor = Executors.newSingleThreadExecutor();
        ExecutorService dealerExecutor = Executors.newFixedThreadPool(properties.dealers());

        carFactoryRoutine.submit(() -> {
            carControllerExecutor.submit(carController::run);
            while (isStarted) {

                motorSuppliers.stream()
                        .filter(DetailSupplier::checkFreeAndPut)
                        .forEach(sup -> motorSupExecutor.submit(sup::supply));

                bodySuppliers.stream()
                        .filter(DetailSupplier::checkFreeAndPut)
                        .forEach(sup -> bodySupExecutor.submit(sup::supply));

                accessorySuppliers.stream()
                        .filter(DetailSupplier::checkFreeAndPut)
                        .forEach(sup -> accessorySupExecutor.submit(sup::supply));

                workers.stream()
                        .filter(Worker::checkFreeAndPut)
                        .forEach(worker -> workerExecutor.submit(worker::create));

                dealers.stream()
                        .filter(Dealer::checkFreeAndPut)
                        .forEach(dealer -> dealerExecutor.submit(dealer::orderCar));

            }
        });

        synchronized (this) {
            this.wait();
        }

        isStarted = false;
        carController.stop();

        motorSupExecutor.shutdownNow();
        bodySupExecutor.shutdownNow();
        accessorySupExecutor.shutdownNow();
        workerExecutor.shutdownNow();
        dealerExecutor.shutdownNow();
        carControllerExecutor.shutdownNow();
        carFactoryRoutine.shutdownNow();

        log.info("[CAR FACTORY] :: finished.");

    }

    public void stop() {
        log.info("[CAR-FACTORY] :: Closing ...");
        isStarted = false;
    }

}
