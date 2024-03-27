package ru.nsu.ccfit.lisitsin.chain.jochain;

import lombok.RequiredArgsConstructor;
import ru.nsu.ccfit.lisitsin.chain.FilterChain;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@RequiredArgsConstructor
public class JavaObjectFilterChain implements FilterChain {

    private final ServerFilter serverFilters;

    @Override
    public void process(BaseDto request) {
        serverFilters.doFilter(request);
    }

}
