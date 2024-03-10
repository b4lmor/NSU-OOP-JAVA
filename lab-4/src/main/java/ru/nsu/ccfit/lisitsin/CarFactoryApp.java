package ru.nsu.ccfit.lisitsin;

import lombok.SneakyThrows;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.nsu.ccfit.lisitsin.configuration.CarFactoryPropertiesConfig;
import ru.nsu.ccfit.lisitsin.factory.CarFactory;
import ru.nsu.ccfit.lisitsin.gui.CarFactoryGui;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableConfigurationProperties(CarFactoryPropertiesConfig.class)
public class CarFactoryApp {

    @SneakyThrows
    public static void main(String[] args) {
        var ctx = new SpringApplicationBuilder(CarFactoryApp.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        CarFactory carFactory = ctx.getBean(CarFactory.class);

        ExecutorService carFactoryExecutor = Executors.newFixedThreadPool(2);

        carFactoryExecutor.submit(() -> new CarFactoryGui().createGui(carFactory));
        carFactoryExecutor.submit(carFactory::start);

    }

}
