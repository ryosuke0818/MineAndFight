package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;


public class Scoreboard {
    private final String displayName = ChatColor.GREEN +"TeamScore";
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
    private Objective objective;
    private Score score;

    public Scoreboard(String gameId) {

        objective = scoreboard.getObjective(gameId);

        score = objective.getScore("TeamScore:");
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    public void setScoreboard(Player player){
        player.setScoreboard(scoreboard);
    }

    public void resetScoreboard(Player player) {
        player.getScoreboard().resetScores("TeamScore:");
    }
}
