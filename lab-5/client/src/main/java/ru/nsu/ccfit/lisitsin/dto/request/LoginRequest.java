package ru.nsu.ccfit.lisitsin.dto.request;

import lombok.Builder;
import lombok.Getter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Builder
@Getter
public class LoginRequest extends BaseDto {

    private final String name;

}
