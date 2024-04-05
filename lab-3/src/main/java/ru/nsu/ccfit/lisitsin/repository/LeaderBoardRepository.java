package ru.nsu.ccfit.lisitsin.repository;

import org.springframework.stereotype.Component;
import ru.nsu.ccfit.lisitsin.repository.database.DiskMap;

import java.util.Map;
import java.util.Set;

@Component
public class LeaderBoardRepository {

    private final DiskMap database = new DiskMap("lab-3/src/main/resources/leaderboard.txt");

    public void save() {
        database.save();
    }

    public void update(String nickname, Integer score) {
        if (!database.containsKey(nickname)
                || database.containsKey(nickname)
                    && Integer.parseInt(database.get(nickname)) < score) {
            database.put(nickname, score.toString());
        }
    }

    public Set<Map.Entry<String, String>> getAll() {
        return database.entrySet();
    }
}
