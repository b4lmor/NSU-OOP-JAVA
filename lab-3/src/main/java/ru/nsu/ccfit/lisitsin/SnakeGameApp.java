package ru.nsu.ccfit.lisitsin;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import ru.nsu.ccfit.lisitsin.frame.MenuFrame;

import java.awt.*;

@SpringBootApplication
public class SnakeGameApp {

    public static void main(String[] args) {

        var ctx = new SpringApplicationBuilder(SnakeGameApp.class)
                .headless(false)
                .web(WebApplicationType.NONE)
                .run(args);

        EventQueue.invokeLater(() -> {
            var ex = ctx.getBean(MenuFrame.class);
            ex.setVisible(true);
        });
    }
}