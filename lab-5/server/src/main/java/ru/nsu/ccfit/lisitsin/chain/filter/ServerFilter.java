package ru.nsu.ccfit.lisitsin.chain.filter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.nsu.ccfit.lisitsin.dto.BaseDto;

@AllArgsConstructor
@NoArgsConstructor
public abstract class ServerFilter {

    protected ServerFilter nextFilter;

    public void doFilter(BaseDto request) {
        if (nextFilter != null) {
            nextFilter.doFilter(request);
        }
    }

    public ServerFilter setNextFilter(ServerFilter nextFilter) {
        this.nextFilter = nextFilter;
        return nextFilter;
    }

    public boolean isEnabled() {
        return false;
    }

}
