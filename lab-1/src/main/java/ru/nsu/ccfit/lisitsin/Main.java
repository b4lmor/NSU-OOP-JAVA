package ru.nsu.ccfit.lisitsin;

import ru.nsu.ccfit.lisitsin.game.Game;

public class Main {
    public static void main(String[] args) {
        try(Game game = new Game()) {
            game.run();
        }
    }
}