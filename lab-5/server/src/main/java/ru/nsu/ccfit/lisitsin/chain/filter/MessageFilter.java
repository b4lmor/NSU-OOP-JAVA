package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@RequiredArgsConstructor
public class MessageFilter extends ServerFilter {

    private final ChatProcessor chatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof MessageRequest messageRequest) {
            chatProcessor.processMessage(messageRequest);
            return;
        }
        super.doFilter(request);
    }

}
