package ru.nsu.ccfit.lisitsin.factory.worker;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.entity.Car;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.AccessoryDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.BodyDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.MotorDetail;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.CarStorage;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.DetailStorage;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

@Log4j2
@RequiredArgsConstructor
public class Worker {

    @Setter
    private long delay = 3000L;

    private final AtomicBoolean isFree = new AtomicBoolean(true);

    private final CarStorage carStorage;

    private final DetailStorage<AccessoryDetail> accessoryStorage;

    private final DetailStorage<BodyDetail> bodyStorage;

    private final DetailStorage<MotorDetail> motorStorage;

    private final int index;

    @Getter
    private boolean isNotAvailable = false;

    @SneakyThrows
    public void create() {

        synchronized (this) {
            wait();
        }
        isNotAvailable = true;

        isFree.set(false);

        var car = Car.builder()
                .accessory(accessoryStorage.poll())
                .body(bodyStorage.poll())
                .motor(motorStorage.poll())
                .build();
        sleep(delay);

        log.info(
                "[WORKER #{}] :: Car created!\n-- car ID: {}\n-- body ID: {}\n-- motor ID: {}\n-- accessory ID: {}",
                index,
                car.getId(),
                car.getBody().getId(),
                car.getMotor().getId(),
                car.getAccessory().getId()
        );


        log.info("[WORKER #{}] :: Sending car ...", index);

        carStorage.push(car);

        log.info("[WORKER #{}] :: Car [{}] pushed!", index, car.getId());

        isFree.set(true);

    }

    public boolean checkFreeAndPut() {
        if (isFree.get()) {
            isFree.set(false);
            return true;
        }
        return false;
    }

    public boolean isFree() {
        return isFree.get();
    }

}
