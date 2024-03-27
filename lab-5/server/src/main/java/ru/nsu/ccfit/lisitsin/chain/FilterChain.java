package ru.nsu.ccfit.lisitsin.chain;

import ru.nsu.ccfit.lisitsin.dto.BaseDto;

public interface FilterChain {

    void process(BaseDto dto);

}
