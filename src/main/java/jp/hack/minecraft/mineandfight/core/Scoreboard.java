package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Team;

import java.util.UUID;

public class Scoreboard {

    Player player;
    Team team;
    Objective scoreboard;

    public Scoreboard(UUID uuid) {
        player = Bukkit.getPlayer(uuid);
        team = player.getScoreboard().getTeam("");
        this.scoreboard = this.player.getScoreboard().getObjective("score");
    }

    public void setTeamScore(int score) {
        team.getScoreboard().getObjective(team.getName()).getScore("TeamScore:").setScore(score);
    }

    public int getTeamScore(UUID uuid) {
        return team.getScoreboard().getObjective(team.getName()).getScore("TeamScore:").getScore();
    }

    public void setScore(int score) {
        scoreboard.getScore("Score:").setScore(score);
    }

    public int getScore() {
        return scoreboard.getScore("Score:").getScore();
    }
}
