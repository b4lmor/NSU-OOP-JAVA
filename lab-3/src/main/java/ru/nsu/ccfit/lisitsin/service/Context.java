package ru.nsu.ccfit.lisitsin.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class Context {

    private int[] x;

    private int[] y;

    private int applesEaten;

    private int appleX;

    private int appleY;

    private GameService.Direction direction = GameService.Direction.RIGHT;

    private boolean running = false;

    @Value("${game.default-snake-size}")
    private int bodyParts;

    @Value("${game.unit-size}")
    private int gameUnits;

    @Value("${game.default-snake-size}")
    private int bodyPartsDefault;

    public Context() {
        this.x = new int[gameUnits];
        this.y = new int[gameUnits];
    }

    public void incrementApples() {
        applesEaten++;
    }

    public void incrementBodyParts() {
        bodyParts++;
    }

    public void reset() {
        running = false;
        x = new int[gameUnits];
        y = new int[gameUnits];
        bodyParts = bodyPartsDefault;
        direction = GameService.Direction.RIGHT;
        applesEaten = 0;
    }
}
