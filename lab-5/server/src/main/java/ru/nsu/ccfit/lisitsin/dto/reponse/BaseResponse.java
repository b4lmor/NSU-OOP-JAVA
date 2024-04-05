package ru.nsu.ccfit.lisitsin.dto.reponse;

import lombok.Getter;
import lombok.Setter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Getter
@Setter
public class BaseResponse extends BaseDto {

    private String message;

    private int code;

}
