package jp.hack.minecraft.mineandfight.core;

import java.util.UUID;

public class Team {
    private final int teamId;
    private String teamColor;
    private int score;

    public Team(int teamId){
        this.teamId = teamId;
    }

    public int getTeamId() {
        return teamId;
    }

    public String getTeamColor() {
        return teamColor;
    }

    public void setTeamColor(String teamColor) {
        this.teamColor = teamColor;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
