package ru.nsu.ccfit.lisitsin.dto.reponse;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class MessagePageResponse extends BaseResponse {

    private List<MessageDto> messages;

    public record MessageDto(

            UUID id,

            UUID authorId,

            String text,

            Date createdAt,

            String name

    ) implements Serializable {
    }

}
