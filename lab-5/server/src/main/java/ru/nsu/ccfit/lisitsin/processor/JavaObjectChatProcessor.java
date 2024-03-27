package ru.nsu.ccfit.lisitsin.processor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;

@Slf4j
@Component
public class JavaObjectChatProcessor {

    public void processMessage(MessageRequest messageRequest) {
        log.debug("Got a msg!");
    }

}
