package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.UUID;


public class Scoreboard {
    private final String displayName = ChatColor.GREEN +"Score";
    private final ScoreboardManager manager = Bukkit.getScoreboardManager();
    private org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();
    private Player player;
    private Objective playerObjective;
    private Score score;
    private Score teamScore;

    public Scoreboard(UUID uuid) {
        player = Bukkit.getPlayer(uuid);

        playerObjective = scoreboard.registerNewObjective("", "dummy", displayName);

        score = playerObjective.getScore("YourScore:");
        teamScore = playerObjective.getScore("TeamScore:");
    }

    public void setTeamScore(int score) {
        teamScore.setScore(score);
    }

    public void setScore(int score) {
        this.score.setScore(score);
    }

    public void setScoreboard(){
        player.setScoreboard(scoreboard);
    }
}
