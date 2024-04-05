package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@RequiredArgsConstructor
public class DisconnectFilter extends ServerFilter {

    private final ChatProcessor chatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof DisconnectRequest disconnectRequest) {
            chatProcessor.processDisconnect(disconnectRequest);
            return;
        }
        super.doFilter(request);
    }

}
