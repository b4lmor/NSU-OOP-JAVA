package ru.nsu.ccfit.lisitsin.dto.request;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Setter
@Getter
public class MessageRequest extends BaseDto {

    private String message;

}
