package ru.nsu.ccfit.lisitsin.factory.entity;

import lombok.Builder;
import lombok.Value;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.AccessoryDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.BodyDetail;
import ru.nsu.ccfit.lisitsin.factory.entity.detail.impl.MotorDetail;

import java.util.UUID;

@Builder
@Value
public class Car {

    AccessoryDetail accessory;

    BodyDetail body;

    MotorDetail motor;

    UUID id = UUID.randomUUID();

}
