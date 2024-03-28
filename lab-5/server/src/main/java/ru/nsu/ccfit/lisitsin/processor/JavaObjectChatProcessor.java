package ru.nsu.ccfit.lisitsin.processor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.dto.request.PageRequest;
import ru.nsu.ccfit.lisitsin.service.MessageService;
import ru.nsu.ccfit.lisitsin.service.UserService;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JavaObjectChatProcessor {

    private final UserService userService;

    private final MessageService messageService;

    public void processMessage(MessageRequest messageRequest) {
        messageService.create(messageRequest);
    }

    public void processPageRequest(PageRequest pageRequest) {
        // TODO: in message service send page to the client
    }

    public void processLogin(LoginRequest loginRequest) {
        userService.create(loginRequest);
    }

    public void processDisconnect(DisconnectRequest disconnectRequest) {
        userService.disconnect(disconnectRequest);
    }

}
