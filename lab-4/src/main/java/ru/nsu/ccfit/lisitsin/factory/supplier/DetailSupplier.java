package ru.nsu.ccfit.lisitsin.factory.supplier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.Detail;
import ru.nsu.ccfit.lisitsin.factory.storage.impl.DetailStorage;

import java.util.concurrent.atomic.AtomicBoolean;

import static java.lang.Thread.sleep;

@RequiredArgsConstructor
@Log4j2
public class DetailSupplier<T extends Detail> {

    @Setter
    private long delay = 1500L;

    private final Class<T> genericClass;

    private final DetailStorage<T> storage;

    @Getter
    private final int index;

    private final AtomicBoolean isFree = new AtomicBoolean(true);

    @SneakyThrows
    public synchronized void supply() {

        sleep(delay);
        T detail = genericClass.newInstance();

        log.info("[SUPPLIER #{}] :: Supplied a detail: [{}, id: {}].", index, genericClass.getSimpleName(), detail.getId());

        storage.push(detail);
        isFree.set(true);
    }

    public boolean checkFreeAndPut() {
        if (isFree.get()) {
            isFree.set(false);
            return true;
        }
        return false;
    }

}
