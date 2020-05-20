package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.Optional;

public class ScoreboardBukkit {
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final Scoreboard scoreboard = manager.getNewScoreboard();
    private Objective objective;

    ScoreboardBukkit(String gameId) {
        Boolean isThereAObjective = scoreboard.getObjectives().stream()
                .filter(objective -> gameId.equals(objective.getName()))
                .findAny()
                .isPresent();
        if (isThereAObjective) {
            objective = scoreboard.getObjective(gameId);
        } else {
            objective = scoreboard.registerNewObjective(gameId, "dummy", "Score");
        }
    }

    public void setScore(String entry, int score) {
        objective.getScore(entry).setScore(score);
    }

    public void setScoreboard(Player player) {
        player.setScoreboard(scoreboard);
    }

    public void resetScoreboard(Player player) {
        player.setScoreboard(manager.getMainScoreboard());
    }
}
