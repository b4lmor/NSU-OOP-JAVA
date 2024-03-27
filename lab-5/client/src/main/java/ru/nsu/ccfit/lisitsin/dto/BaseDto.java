package ru.nsu.ccfit.lisitsin.dto;

import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Setter
public class BaseDto implements Serializable {

    private UUID authorId;

}
