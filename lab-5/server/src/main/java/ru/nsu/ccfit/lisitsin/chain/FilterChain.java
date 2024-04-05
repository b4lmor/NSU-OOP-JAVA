package ru.nsu.ccfit.lisitsin.chain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.configuration.DataTransferProtocol;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@Slf4j
@RequiredArgsConstructor
public class FilterChain {

    private final ServerFilter serverFilters;

    private final DataTransferProtocol dataTransferProtocol;

    public void process(BaseDto request) {
        switch (dataTransferProtocol) {
            case JAVA_OBJECT -> serverFilters.doFilter(request);
            case XML -> log.error("[FILTER CHAIN] :: XML isn't implemented"); // TODO
        }
    }

}
