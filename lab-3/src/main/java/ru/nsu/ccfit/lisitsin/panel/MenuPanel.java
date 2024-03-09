package ru.nsu.ccfit.lisitsin.panel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.service.GameService;

import javax.swing.*;
import java.awt.*;

@Component
@Slf4j
public class MenuPanel extends JPanel {
    @Autowired
    public MenuPanel(GameService gameService, SnakePanel snakePanel) {
        this.setPreferredSize(new Dimension(
                gameService.getScreenWidth(),
                gameService.getScreenHeight()
        ));
        this.setLayout(new GridLayout(3, 1));

        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> {
            log.info("Start Game button clicked");
            add(snakePanel);
        });

        JButton leaderboardButton = new JButton("Leaderboard");
        leaderboardButton.addActionListener(e -> log.info("Leaderboard button clicked"));

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(e -> System.exit(0));

        add(startButton);
        add(leaderboardButton);
        add(exitButton);
    }
}
