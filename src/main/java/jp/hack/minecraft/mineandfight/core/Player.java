package jp.hack.minecraft.mineandfight.core;

import java.util.UUID;

public class Player {
    private final UUID uuid;
    private int teamId;
    private int score;

    public Player(UUID uuid){
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
