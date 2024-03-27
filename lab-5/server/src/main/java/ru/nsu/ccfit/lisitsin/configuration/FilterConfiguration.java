package ru.nsu.ccfit.lisitsin.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.nsu.ccfit.lisitsin.chain.FilterChain;
import ru.nsu.ccfit.lisitsin.chain.filter.ServerFilter;
import ru.nsu.ccfit.lisitsin.chain.filter.jofilter.MessageFilter;
import ru.nsu.ccfit.lisitsin.chain.jochain.JavaObjectFilterChain;
import ru.nsu.ccfit.lisitsin.processor.JavaObjectChatProcessor;

@Configuration
public class FilterConfiguration {

    @Autowired
    @Bean
    @ConditionalOnProperty(prefix = "chat", name = "server.data-transfer-protocol", havingValue = "java_object")
    public ServerFilter javaObjectFilters(JavaObjectChatProcessor javaObjectChatProcessor) {
        MessageFilter messageFilter = new MessageFilter(javaObjectChatProcessor);
        // TODO: others
        return messageFilter;
    }

    @Autowired
    @Bean
    @ConditionalOnProperty(prefix = "chat", name = "server.data-transfer-protocol", havingValue = "java_object")
    public FilterChain javaObjectFilterChain(ServerFilter javaObjectFilters) {
        return new JavaObjectFilterChain(javaObjectFilters);
    }

}
