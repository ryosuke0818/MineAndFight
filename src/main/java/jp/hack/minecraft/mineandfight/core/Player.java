package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;

import java.util.UUID;

public class Player {
    private final UUID uuid;
    private String name;
    private int teamId;
    private int score;
    private int bounty;

    public Player(UUID uuid){
        this.uuid = uuid;
        name = Bukkit.getPlayer(uuid).getName();
    }

    public UUID getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
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

    public int getBounty() {
        return bounty;
    }

    public void setBounty(int bounty) {
        this.bounty = bounty;
    }
}
