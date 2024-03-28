package ru.nsu.ccfit.lisitsin.chain.filter.jofilter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.processor.JavaObjectChatProcessor;

@RequiredArgsConstructor
public class LoginFilter extends ServerFilter {

    private final JavaObjectChatProcessor javaObjectChatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof LoginRequest loginRequest) {
            javaObjectChatProcessor.processLogin(loginRequest);
            return;
        }
        super.doFilter(request);
    }

}
