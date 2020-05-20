package jp.hack.minecraft.mineandfight.core;

import org.bukkit.entity.Player;

public class Scoreboard {
    private final ScoreboardBukkit scoreboardBukkit;
    private final String gameId;

    public Scoreboard(String gameId) {
        this.gameId = gameId;
        scoreboardBukkit = new ScoreboardBukkit(gameId);
    }

    public String getGameId() {
        return gameId;
    }

    public void setScore(String playerName, int score) {
        scoreboardBukkit.setScore(playerName, score);
    }

    public void setScoreboard(Player player) {
        scoreboardBukkit.setScoreboard(player);
    }

    public void resetScoreboard(Player player) {
        scoreboardBukkit.resetScoreboard(player);
    }
}
