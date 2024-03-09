package ru.nsu.ccfit.lisitsin.game.service;

import ru.nsu.ccfit.lisitsin.game.entity.Answer;
import ru.nsu.ccfit.lisitsin.game.entity.GuessResult;

public interface GameService {

    Answer provideRandomAnswer();

    GuessResult guess(Answer answer, Answer guess);

}
