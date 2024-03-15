package ru.nsu.ccfit.lisitsin.configuration;


import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "chat.server", ignoreUnknownFields = false)
public record ServerPropertiesConfiguration(

        @Positive
        int port

) {
}
