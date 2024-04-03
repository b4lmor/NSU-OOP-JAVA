package ru.nsu.ccfit.lisitsin.processor;

import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import ru.nsu.ccfit.lisitsin.dto.reponse.BaseResponse;
import ru.nsu.ccfit.lisitsin.dto.reponse.MessagePageResponse;
import ru.nsu.ccfit.lisitsin.entity.Message;

import java.util.UUID;

@UtilityClass
public class Responses {

    public static BaseResponse getOk(UUID id) {
        var response = new BaseResponse();
        response.setCode(Codes.OK.code);
        response.setMessage("Ok");
        response.setAuthorId(id);
        return response;
    }

    public static MessagePageResponse getMessagePage(UUID id, Page<Message> messages) {
        var response = new MessagePageResponse();
        response.setCode(Codes.OK.code);
        response.setMessage("Ok");
        response.setAuthorId(id);
        response.setMessages(messages.stream()
                .map(message -> new MessagePageResponse.MessageDto(
                        message.getId(),
                        message.getUser().getId(),
                        message.getText(),
                        message.getCreatedAt()
                ))
                .toList());
        return response;
    }

    public static BaseResponse getLoginIsAlreadyTaken(UUID id) {
        var response = new BaseResponse();
        response.setCode(Codes.CONFLICT.code);
        response.setMessage("Login is already taken!");
        response.setAuthorId(id);
        return response;
    }

    @RequiredArgsConstructor
    public enum Codes {

        OK(1),

        CONFLICT(2),

        BAD_REQUEST(3);

        private final int code;

    }
}
