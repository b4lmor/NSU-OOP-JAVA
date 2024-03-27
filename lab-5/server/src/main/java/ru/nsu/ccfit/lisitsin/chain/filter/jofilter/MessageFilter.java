package ru.nsu.ccfit.lisitsin.chain.filter.jofilter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.MessageRequest;
import ru.nsu.ccfit.lisitsin.processor.JavaObjectChatProcessor;

@RequiredArgsConstructor
public class MessageFilter extends ServerFilter {

    private final JavaObjectChatProcessor javaObjectChatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof MessageRequest messageRequest) {
            javaObjectChatProcessor.processMessage(messageRequest);
            return;
        }
        super.doFilter(request);
    }

}
