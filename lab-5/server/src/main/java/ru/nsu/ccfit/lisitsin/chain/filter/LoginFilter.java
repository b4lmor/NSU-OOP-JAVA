package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.LoginRequest;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@RequiredArgsConstructor
public class LoginFilter extends ServerFilter {

    private final ChatProcessor chatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof LoginRequest loginRequest) {
            chatProcessor.processLogin(loginRequest);
            return;
        }
        super.doFilter(request);
    }

}
