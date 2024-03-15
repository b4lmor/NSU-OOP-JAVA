package ru.nsu.ccfit.lisitsin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.ccfit.lisitsin.configuration.ClientPropertiesConfiguration;

@SpringBootApplication
@EnableConfigurationProperties(ClientPropertiesConfiguration.class)
public class ClientApplication {
    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(ClientApplication.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

    }

}