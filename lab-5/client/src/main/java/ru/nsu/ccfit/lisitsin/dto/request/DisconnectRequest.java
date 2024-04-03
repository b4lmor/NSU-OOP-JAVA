package ru.nsu.ccfit.lisitsin.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Getter
@Setter
public class DisconnectRequest extends BaseDto {

    private String name;

}
