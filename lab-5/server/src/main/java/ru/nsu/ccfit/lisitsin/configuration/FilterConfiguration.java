package ru.nsu.ccfit.lisitsin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.ccfit.lisitsin.chain.FilterChain;
import ru.nsu.ccfit.lisitsin.chain.filter.DisconnectFilter;
import ru.nsu.ccfit.lisitsin.chain.filter.LoginFilter;
import ru.nsu.ccfit.lisitsin.chain.filter.MessageFilter;
import ru.nsu.ccfit.lisitsin.chain.filter.MessagePageFilter;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.processor.ChatProcessor;

@Configuration
public class FilterConfiguration {

    @Autowired
    @Bean
    public ServerFilter filters(ChatProcessor chatProcessor) {
        MessageFilter messageFilter = new MessageFilter(chatProcessor);

        messageFilter
                .setNextFilter(new MessagePageFilter(chatProcessor))
                .setNextFilter(new LoginFilter(chatProcessor))
                .setNextFilter(new DisconnectFilter(chatProcessor));

        return messageFilter;
    }

    @Autowired
    @Bean
    public FilterChain filterChain(ServerFilter filters, ServerPropertiesConfiguration serverPropertiesConfiguration) {
        return new FilterChain(filters, serverPropertiesConfiguration.dataTransferProtocol());
    }

}
