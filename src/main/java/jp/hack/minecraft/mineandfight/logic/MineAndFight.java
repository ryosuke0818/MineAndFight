package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.Game;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        logger.info(String.format("onBlockBreakEvent: %s", event.getPlayer().getName()));

    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        String killed = event.getEntity().getName();
        String killer = event.getEntity().getKiller().getName();
        logger.info(String.format("onPlayerDeathEvent: %s -> %s", killer, killed));
    }

}
