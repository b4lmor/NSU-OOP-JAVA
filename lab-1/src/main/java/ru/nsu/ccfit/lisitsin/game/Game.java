package ru.nsu.ccfit.lisitsin.game;

import ru.nsu.ccfit.lisitsin.game.entity.Answer;
import ru.nsu.ccfit.lisitsin.game.service.GameService;
import ru.nsu.ccfit.lisitsin.game.service.impl.DefaultGameService;

import java.util.Collections;
import java.util.Scanner;

public class Game implements AutoCloseable {
    private final GameService gameService = new DefaultGameService();
    private final Scanner scanner = new Scanner(System.in);

    public void run() {
        System.out.println("начинаем игру. напиши 'stop', если надоело играть.");
        var answer = gameService.provideRandomAnswer();
        while (scanner.hasNext()) {
            var line = scanner.nextLine();
            if (line.equals("stop")) {
                System.out.println("игра окончена!");
                System.out.println(answer.digits().stream().toList());
                break;
            }
            try {
                var guess = Answer.of(Integer.parseInt(line));
                if (guess == null) {
                    System.out.println("число должно состоять из 4 неповторяющихся цифр!");
                } else if (answer.equals(guess)) {
                    System.out.println("верно!");
                    break;
                } else {
                    var res = gameService.guess(answer, guess);
                    System.out.println("мимо!");
                    System.out.println("- быки:   " + res.bulls());
                    System.out.println("- коровы: " + res.cows());
                }
            } catch (Exception e) {
                System.out.println("не понимаю тебя...");
            }
        }
    }

    @Override
    public void close() {
        scanner.close();
    }
}
