package ru.nsu.ccfit.lisitsin.frame;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.panel.SnakePanel;

import javax.swing.*;

@Component
public class SnakeFrame extends JFrame {
    private final SnakePanel snakePanel;

    @Autowired
    public SnakeFrame(SnakePanel snakePanel,
                      @Value("${game.title}") String title) {
        this.snakePanel = snakePanel;
        this.add(snakePanel);
        this.setTitle(title);
        this.setResizable(false);
        this.pack();
        this.setLocationRelativeTo(null);
    }

    public void run(String nickname, MenuFrame menuFrame) {
        snakePanel.startGame(nickname, menuFrame);
    }
}
