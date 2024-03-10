package ru.nsu.ccfit.lisitsin.factory.dealer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.entity.Car;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.CarStorage;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Math.abs;
import static java.lang.Thread.sleep;

@Log4j2
@RequiredArgsConstructor
public class Dealer {

    @Setter
    private long delay = 5000L;

    @Getter
    private final Map<Car, BigInteger> cars = new HashMap<>();

    private final AtomicBoolean isFree = new AtomicBoolean(true);

    private final CarStorage carStorage;

    private final int index;

    @SneakyThrows
    public synchronized void orderCar() {

        isFree.set(false);

        log.info("[DEALER #{}] :: Ordering a car ...", index);

        var car = carStorage.poll();

        cars.put(car, countPrice(car));

        log.info(
                "[DEALER #{}] :: Done! (income: {}$)",
                index,
                countTotalIncome()
        );

        sleep(delay);

        isFree.set(true);
    }

    public boolean checkFreeAndPut() {
        if (isFree.get()) {
            isFree.set(false);
            return true;
        }
        return false;
    }

    public BigInteger countTotalIncome() {
        return cars.values().stream().reduce(BigInteger::add).orElse(BigInteger.ZERO);
    }

    private BigInteger countPrice(Car car) {
        return BigInteger.valueOf(abs(car.hashCode() % 10000));
    }

}
