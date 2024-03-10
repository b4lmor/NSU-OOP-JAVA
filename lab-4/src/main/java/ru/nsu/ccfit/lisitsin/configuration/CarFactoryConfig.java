package ru.nsu.ccfit.lisitsin.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CarFactoryConfig {

    private final CarFactoryPropertiesConfig propertiesConfig;
    private final ApplicationContext ctx;

}
