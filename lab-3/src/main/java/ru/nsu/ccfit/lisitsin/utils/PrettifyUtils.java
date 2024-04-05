package ru.nsu.ccfit.lisitsin.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@UtilityClass
public class PrettifyUtils {

    public String getSortedLeaderBoardText(Set<Map.Entry<String, String>> leaders, int limit) {
        List<Map.Entry<String, String>> sortedList = new ArrayList<>(leaders);

        sortedList.sort((entry1, entry2) -> {
            Integer value1 = Integer.parseInt(entry1.getValue());
            Integer value2 = Integer.parseInt(entry2.getValue());
            return value2.compareTo(value1);
        });

        StringBuilder leaderboardText = new StringBuilder();
        for (int i = 0; i < limit; i++) {
            Map.Entry<String, String> entry = sortedList.get(i);
            leaderboardText.append(i + 1)
                    .append(". ")
                    .append(entry.getKey())
                    .append(": ")
                    .append(entry.getValue());
            if (i < sortedList.size() - 1) {
                leaderboardText.append("\n");
            }
        }

        return leaderboardText.toString();
    }


}
