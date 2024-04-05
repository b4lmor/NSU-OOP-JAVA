package ru.nsu.ccfit.lisitsin.configuration;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import static ru.nsu.ccfit.lisitsin.validation.Validator.HOST_REGEX;

@Validated
@ConfigurationProperties(prefix = "chat.server", ignoreUnknownFields = false)
public record ClientPropertiesConfiguration(

        @Positive
        int port,

        @Pattern(regexp = HOST_REGEX)
        String host

) {
}
