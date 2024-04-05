package ru.nsu.ccfit.lisitsin.factory.entity.detail;

import lombok.Getter;

import java.util.UUID;

@Getter
abstract public class Detail {

    private final UUID id;

    public Detail() {
        this.id = UUID.randomUUID();
    }

}
