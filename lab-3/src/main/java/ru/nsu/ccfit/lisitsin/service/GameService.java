package ru.nsu.ccfit.lisitsin.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.nsu.ccfit.lisitsin.panel.SnakePanel;
import ru.nsu.ccfit.lisitsin.repository.LeaderBoardRepository;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

@Service
public class GameService {

    @Getter
    @Value("${game.window-size.width}")
    private Integer screenWidth;

    @Getter
    @Value("${game.window-size.height}")
    private int screenHeight;

    @Getter
    @Value("${game.unit-size}")
    private int unitSize;

    @Getter
    @Value("${game.default-snake-size}")
    private int snakeSizeDefault;

    @Getter
    @Value("${game.delay}")
    private int delay;

    @Getter
    @Value("#{${game.window-size.width} * ${game.window-size.height} / (${game.unit-size} * ${game.unit-size})}")
    private int gameUnits;

    @Getter
    private final Context context;

    private final Random random;

    private final LeaderBoardRepository leaderBoardRepository;

    @Autowired
    public GameService(Context context, LeaderBoardRepository leaderBoardRepository) {
        this.leaderBoardRepository = leaderBoardRepository;
        this.context = context;
        random = new Random();
    }

    public void newApple() {
        context.setAppleX(random.nextInt((screenWidth / unitSize)) * unitSize);
        context.setAppleY(random.nextInt((screenHeight / unitSize)) * unitSize);
    }

    public void move() {
        for (int i = context.getBodyParts(); i > 0; i--) {
            context.getX()[i] = context.getX()[i - 1];
            context.getY()[i] = context.getY()[i - 1];
        }

        var dir = context.getDirection();
        switch (dir) {
            case UP:
                context.getY()[0] = context.getY()[0] - unitSize;
                break;
            case DOWN:
                context.getY()[0] = context.getY()[0] + unitSize;
                break;
            case LEFT:
                context.getX()[0] = context.getX()[0] - unitSize;
                break;
            case RIGHT:
                context.getX()[0] = context.getX()[0] + unitSize;
                break;
        }

    }

    public void checkApple() {
        if ((context.getX()[0] == context.getAppleX()) && (context.getY()[0] == context.getAppleY())) {
            context.incrementApples();
            context.incrementBodyParts();
            newApple();
        }
    }

    public boolean isCrossing() {
        for (int i = context.getBodyParts(); i > 0; i--) {
            if (context.getX()[0] == context.getX()[i]
                    && context.getY()[0] == context.getY()[i]) {
                context.setRunning(false);
            }
        }

        if (context.getX()[0] < 0 || context.getX()[0] > screenWidth || context.getY()[0] < 0 || context.getY()[0] > screenHeight) {
            context.setRunning(false);
        }

        return context.isRunning();
    }

    public void drawGameScene(Graphics g, SnakePanel snakePanel) {
        g.setColor(Color.red);
        g.fillOval(
                getContext().getAppleX(),
                getContext().getAppleY(),
                getUnitSize(),
                getUnitSize()
        );

        for (int i = 0; i < getContext().getBodyParts(); i++) {
            g.setColor(
                    i == 0
                    ? Color.green
                    : new Color(45, 180, 0)
            );
            g.fillRect(
                    getContext().getX()[i],
                    getContext().getY()[i],
                    getUnitSize(),
                    getUnitSize()
            );
        }

        drawScore(g, snakePanel);
    }

    public void drawGameOver(Graphics g, SnakePanel snakePanel) {
        drawScore(g, snakePanel);

        Font font = new Font("", Font.BOLD, 75);
        drawText(
                g,
                Color.red,
                font,
                "Game over",
                (getScreenWidth() - snakePanel.getFontMetrics(font).stringWidth("Game Over")) / 2,
                getScreenHeight() / 2
        );
    }

    public void drawScore(Graphics g, SnakePanel snakePanel) {
        Font font = new Font("", Font.BOLD, 40);

        drawText(
                g,
                Color.red,
                font,
                "Score: " + getContext().getApplesEaten(),
                (getScreenWidth() - snakePanel.getFontMetrics(font)
                        .stringWidth("Score: " + getContext().getApplesEaten())) / 2,
                font.getSize()
        );
    }

    public void drawText(Graphics g, Color color, Font font, String text, int x, int y) {
        g.setColor(color);
        g.setFont(font);
        g.drawString(text, x, y);
    }

    public void startGame() {
        this.context.reset();
        this.context.setRunning(true);
    }

    public void saveResults(String nickname) {
        leaderBoardRepository.update(nickname, context.getApplesEaten());
        leaderBoardRepository.save();
    }

    public KeyAdapter getKeyAdapter() {
        return new MyKeyAdapter(context);
    }

    public enum Direction {
        RIGHT,
        LEFT,
        UP,
        DOWN
    }

    private static class MyKeyAdapter extends KeyAdapter {

        private final Context context;

        private MyKeyAdapter(Context context) {
            this.context = context;
        }

        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (!context.getDirection().equals(Direction.RIGHT)) {
                        context.setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (!context.getDirection().equals(Direction.LEFT)) {
                        context.setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (!context.getDirection().equals(Direction.DOWN)) {
                        context.setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (!context.getDirection().equals(Direction.UP)) {
                        context.setDirection(Direction.DOWN);
                    }
                    break;
            }
        }
    }
}
