package ru.nsu.ccfit.lisitsin.panel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.frame.MenuFrame;
import ru.nsu.ccfit.lisitsin.service.GameService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class SnakePanel extends JPanel implements ActionListener {
    private final GameService gameService;
    private String nickname;
    private Timer timer;
    private MenuFrame menuFrameReference;

    @Autowired
    public SnakePanel(GameService gameService) {
        this.gameService = gameService;
        this.setPreferredSize(new Dimension(
                gameService.getScreenWidth(),
                gameService.getScreenHeight()
        ));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(gameService.getKeyAdapter());
    }

    public void startGame(String nickname, MenuFrame menuFrame) {
        this.nickname = nickname;
        this.menuFrameReference = menuFrame;
        gameService.newApple();
        gameService.startGame();
        timer = new Timer(gameService.getDelay(), this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawGameScene(g);
    }

    public void drawGameScene(Graphics g) {
        if (gameService.getContext().isRunning()) {
            gameService.drawGameScene(g, this);
        } else {
            gameService.saveResults(nickname);
            gameService.drawGameOver(g, this);
            menuFrameReference.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameService.getContext().isRunning()) {
            gameService.move();
            gameService.checkApple();
            if (!gameService.isCrossing()) {
                timer.stop();
            }
        }
        repaint();
    }

}

