package ru.nsu.ccfit.lisitsin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.ccfit.lisitsin.configuration.ServerPropertiesConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ServerPropertiesConfiguration.class)
public class ServerApplication {
    public static void main(String[] args) {

        new SpringApplicationBuilder(ServerApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

    }

}