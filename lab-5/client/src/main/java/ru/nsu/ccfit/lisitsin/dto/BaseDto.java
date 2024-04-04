package ru.nsu.ccfit.lisitsin.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
@Getter
public abstract class BaseDto implements Serializable {

    private UUID authorId;

}
