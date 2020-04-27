package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.LogManager;
import java.util.logging.Logger;

public class MineAndFight implements Listener {
    Logger logger;

    private final Game game;
    public MineAndFight(Game game) {
        this.game = game;
        this.logger = game.getPlugin().getLogger();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        logger.info(String.format("onLogin: %s", event.getPlayer().getName()));
    }

}
