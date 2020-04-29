package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.Player;
import jp.hack.minecraft.mineandfight.core.Team;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class MineAndFight implements Listener {
    private Logger logger;

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
        logger.info(String.format("onPlayerDeathEvent: %s -> %s", event.getEntity().getName(), event.getEntity().getKiller().getName()));

        Player killed = game.findPlayer(event.getEntity().getUniqueId());
        Player killer = game.findPlayer(event.getEntity().getKiller().getUniqueId());

        Team killerTeam = new Team(killer.getTeamId());

        killer.setScore(killer.getScore() + killed.getBounty());
        killer.setBounty(killer.getBounty() + 1);
        killerTeam.setScore(killerTeam.getScore() + killed.getBounty());
        killed.setBounty(0);
    }
}
