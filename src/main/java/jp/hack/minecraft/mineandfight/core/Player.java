package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public class Player {
    private final UUID uuid;
    private String name;
    private int teamId;
    private int score;
    private int bounty;
    private Location spawnLocation;
    private Location firstLocation;
    private Inventory firstInventory;
    private Boolean isPlayingGame;

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

    public Location getRespawnLocation() {
        return spawnLocation;
    }

    public void setRespawnLocation(Location location) {
        spawnLocation = location;
    }

    public Location getFirstLocation() {
        return firstLocation;
    }

    public void setFirstLocation(Location location) {
        firstLocation = location;
    }

    public Inventory getFirstInventory() {
        return firstInventory;
    }

    public void setFirstInventory(Inventory inventory) {
        firstInventory = inventory;
    }

    public Boolean isPlayingGame() {
        return isPlayingGame;
    }

    public void setPlayingGame(Boolean playingGame) {
        isPlayingGame = playingGame;
    }
}
