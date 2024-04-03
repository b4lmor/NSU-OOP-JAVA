package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.MessagePageRequest;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@RequiredArgsConstructor
public class MessagePageFilter extends ServerFilter {

    private final ChatProcessor chatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof MessagePageRequest messagePageRequest) {
            chatProcessor.processPageRequest(messagePageRequest);
            return;
        }
        super.doFilter(request);
    }

}
