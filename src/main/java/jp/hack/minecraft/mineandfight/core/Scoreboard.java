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

    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private final org.bukkit.scoreboard.Scoreboard scoreboard = manager.getNewScoreboard();

    private final Objective playerObjective;
    private final Objective teamObjective;

    private Score teamScore;
    private Score score;

    private final Player player;

    public Scoreboard(UUID uuid, String teamName) {
        player = Bukkit.getPlayer(uuid);
        playerObjective = scoreboard.registerNewObjective(player.getName(), "dummy", displayName);
        teamObjective = scoreboard.getObjective(teamName);
        score = playerObjective.getScore("YourScore:");
        teamScore = teamObjective.getScore("TeamScore:");
    }

    public void setTeamScore(int score) {
        teamScore.setScore(score);
        player.setScoreboard(scoreboard);
    }

    public int getTeamScore() {
        return teamScore.getScore();
    }

    public void setScore(int score) {
        this.score.setScore(score);
        player.setScoreboard(scoreboard);
    }

    public int getScore() {
        return score.getScore();
    }
}
