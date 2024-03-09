package ru.nsu.ccfit.lisitsin.game.service.impl;

import ru.nsu.ccfit.lisitsin.game.entity.Answer;
import ru.nsu.ccfit.lisitsin.game.entity.GuessResult;
import ru.nsu.ccfit.lisitsin.game.service.GameService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

public class DefaultGameService implements GameService {

    private final List<Integer> immutableDigits
            = IntStream.range(0, 10)
            .boxed()
            .toList();

    @Override
    public Answer provideRandomAnswer() {
        List<Integer> digits = new ArrayList<>(immutableDigits);

        Collections.shuffle(digits);
        while (digits.get(0) == 0) {
            Collections.shuffle(digits);
        }

        return new Answer(digits.subList(0, 4));
    }

    @Override
    public GuessResult guess(Answer answer, Answer guess) {
        int cows = IntStream.range(0, 4)
                .reduce(
                        0,
                        (a, b) -> a +
                                (answer.digits().contains(guess.digits().get(b))
                                        && !answer.digits().get(b).equals(guess.digits().get(b))
                                        ? 1 : 0)
                );

        int bulls = IntStream.range(0, 4)
                .reduce(
                        0,
                        (a, b) -> a +
                                (answer.digits().get(b).equals(guess.digits().get(b))
                                        ? 1 : 0)
                );

        return new GuessResult(cows, bulls);
    }
}
