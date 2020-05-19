package jp.hack.minecraft.mineandfight.core;

import org.bukkit.entity.Player;

public class Scoreboard {
    private final ScoreboardBukkit scoreboardBukkit;
    private final String gameId;
    private final int teamId;
    private int score = 0;

    public Scoreboard(String gameId, int teamId) {
        this.gameId = gameId;
        this.teamId = teamId;
        scoreboardBukkit = new ScoreboardBukkit(gameId);
    }

    public String getGameId() {
        return gameId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setScore(int score) {
        this.score = score;
        scoreboardBukkit.setScore("Team Score:", this.score);
    }

    public int getScore() {
        return score;
    }

    public void setScoreboard(Player player) {
        scoreboardBukkit.setScoreboard(player);
    }

    public void resetScoreboard(Player player) {
        scoreboardBukkit.resetScoreboard(player);
    }
}
