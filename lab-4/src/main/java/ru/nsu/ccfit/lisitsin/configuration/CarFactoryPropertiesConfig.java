package ru.nsu.ccfit.lisitsin.configuration;

import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "factory", ignoreUnknownFields = false)
public record CarFactoryPropertiesConfig(

        @Positive
        int storageAccessorySize,

        @Positive
        int storageBodySize,

        @Positive
        int storageMotorSize,

        @Positive
        int storageCarSize,

        @Positive
        int accessorySuppliers,

        @Positive
        int bodySuppliers,

        @Positive
        int motorSuppliers,

        @Positive
        int workers,

        @Positive
        int dealers,

        boolean logSale

) {
}
