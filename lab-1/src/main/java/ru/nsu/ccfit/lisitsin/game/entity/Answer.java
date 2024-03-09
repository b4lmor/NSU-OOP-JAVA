package ru.nsu.ccfit.lisitsin.game.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public record Answer(List<Integer> digits) {
    public Answer {
        if (digits.size() != 4) {
            throw new IllegalArgumentException();
        }
    }

    public static Answer of(int number) {
        if (1000 > number || 9999 < number) {
            return null;
        }

        var digits = numberToList(number);

        if (new HashSet<>(digits).size() != 4) {
            return null;
        }

        return new Answer(digits);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof Answer ans) {
            return this.digits.equals(ans.digits);
        }
        return false;
    }

    private static List<Integer> numberToList(int number) {
        return IntStream.iterate(number, n -> n / 10)
                .limit(String.valueOf(number).length())
                .map(n -> n % 10)
                .boxed()
                .toList();
    }
}
