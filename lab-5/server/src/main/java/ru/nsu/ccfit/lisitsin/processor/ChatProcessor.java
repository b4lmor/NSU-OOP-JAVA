package ru.nsu.ccfit.lisitsin.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.configuration.DataTransferProtocol;
import ru.nsu.ccfit.lisitsin.configuration.ServerPropertiesConfiguration;
import ru.nsu.ccfit.lisitsin.dto.reponse.BaseResponse;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.dto.request.MessagePageRequest;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.server.ConnectionManager;
import ru.nsu.ccfit.lisitsin.service.MessageService;
import ru.nsu.ccfit.lisitsin.service.UserService;

import static ru.nsu.ccfit.lisitsin.processor.Responses.getLoginIsAlreadyTaken;
import static ru.nsu.ccfit.lisitsin.processor.Responses.getMessagePage;
import static ru.nsu.ccfit.lisitsin.processor.Responses.getOk;

@Slf4j
@Component
public class ChatProcessor {

    private final UserService userService;

    private final MessageService messageService;

    private final DataTransferProtocol dataTransferProtocol;

    @Autowired
    public ChatProcessor(UserService userService, MessageService messageService, ServerPropertiesConfiguration serverPropertiesConfiguration) {
        this.userService = userService;
        this.messageService = messageService;
        this.dataTransferProtocol = serverPropertiesConfiguration.dataTransferProtocol();
    }

    public void processMessage(MessageRequest messageRequest) {
        log.info("[PROCESSOR] :: Saving message ...");
        messageService.create(messageRequest);
        log.info("[PROCESSOR] :: Saving message ... Done!");
        userService.update(messageRequest.getAuthorId());
        answer(getOk(messageRequest.getAuthorId()));
    }

    public void processPageRequest(MessagePageRequest messagePageRequest) {
        log.info("[PROCESSOR] :: Sending messages ...");
        var messages = messageService.findMessagesSortedByCreatedAt(messagePageRequest);
        answer(getMessagePage(messagePageRequest.getAuthorId(), messages));
        log.info("[PROCESSOR] :: Sending messages ... Done!");
    }

    public void processLogin(LoginRequest loginRequest) {
        if (userService.isLoginTaken(loginRequest)) {
            answer(getLoginIsAlreadyTaken(loginRequest.getAuthorId()));
        } else {
            log.info("[PROCESSOR] :: Saving user ...");
            userService.create(loginRequest);
            log.info("[PROCESSOR] :: Saving user ... Done!");
            answer(getOk(loginRequest.getAuthorId()));
        }
    }

    public void processDisconnect(DisconnectRequest disconnectRequest) {
        log.info("[PROCESSOR] :: Disconnecting user ...");
        userService.disconnect(disconnectRequest);
        log.info("[PROCESSOR] :: Disconnecting user ... Done!");
        userService.update(disconnectRequest.getAuthorId());
        answer(getOk(disconnectRequest.getAuthorId()));
    }

    public void answer(BaseResponse response) {
        log.info("[PROCESSOR] :: Answering ...");
        switch (dataTransferProtocol) {
            case JAVA_OBJECT -> ConnectionManager.get(response.getAuthorId())
                    .ifPresent(connection -> connection.send(response));
            case XML -> log.error("[PROCESSOR] :: XML isn't implemented"); // TODO
        }
        log.info("[PROCESSOR] :: Answering ... Done!");
    }

}
