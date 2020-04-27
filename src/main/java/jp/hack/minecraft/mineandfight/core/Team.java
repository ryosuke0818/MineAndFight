package jp.hack.minecraft.mineandfight.core;

public class Team {
    private final int teamId;
    private String teamColor;

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
}
