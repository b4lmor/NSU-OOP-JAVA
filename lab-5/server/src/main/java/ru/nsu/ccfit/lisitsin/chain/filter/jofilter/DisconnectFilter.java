package ru.nsu.ccfit.lisitsin.chain.filter.jofilter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.DisconnectRequest;
import ru.nsu.ccfit.lisitsin.processor.JavaObjectChatProcessor;

@RequiredArgsConstructor
public class DisconnectFilter extends ServerFilter {

    private final JavaObjectChatProcessor javaObjectChatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof DisconnectRequest disconnectRequest) {
            javaObjectChatProcessor.processDisconnect(disconnectRequest);
            return;
        }
        super.doFilter(request);
    }

}
