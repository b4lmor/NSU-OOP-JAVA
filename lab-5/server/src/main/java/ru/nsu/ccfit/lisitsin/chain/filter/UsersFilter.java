package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;
import ru.nsu.ccfit.lisitsin.dto.request.UsersRequest;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@RequiredArgsConstructor
public class UsersFilter extends ServerFilter {

    private final ChatProcessor chatProcessor;

    @Override
    public void doFilter(BaseDto request) {
        if (request instanceof UsersRequest usersRequest) {
            chatProcessor.processUsers(usersRequest);
            return;
        }
        super.doFilter(request);
    }

}
