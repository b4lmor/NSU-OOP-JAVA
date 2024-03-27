package ru.nsu.ccfit.lisitsin.dto.request;

import lombok.Builder;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Builder
public class MessageRequest extends BaseDto {

    private final String username;

    private final String message;

}
