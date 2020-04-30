package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.Player;
import jp.hack.minecraft.mineandfight.core.Scoreboard;
import jp.hack.minecraft.mineandfight.core.Team;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.logging.Logger;

public class MineAndFight implements Listener {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFight");

    private final Game game;
    public MineAndFight(Game game) {
        this.game = game;
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        LOGGER.info(String.format("onLogin: %s", event.getPlayer().getName()));
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event){
        LOGGER.info(String.format("onBlockBreakEvent: %s", event.getPlayer().getName()));

        Player breaker = game.findPlayer(event.getPlayer().getUniqueId());

        final String oreName = Material.EMERALD_ORE.getData().getName();
        String blockName = event.getBlock().getBlockData().getMaterial().getData().getName();

        if(blockName.equals(oreName)){
            breaker.setScore( breaker.getScore() + ( breaker.getBounty() + 1 ) );
            breaker.setBounty(0);
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
<<<<<<< HEAD
        if (event.getEntity().getKiller() != null) {
            Player killed = game.findPlayer(event.getEntity().getUniqueId());
            Player killer = game.findPlayer(event.getEntity().getKiller().getUniqueId());
=======
        LOGGER.info(String.format("onPlayerDeathEvent: %s -> %s", event.getEntity().getName(), event.getEntity().getKiller().getName()));
>>>>>>> 0e925faa164ec2ab829eae566c1777e3dda4caa4

            logger.info(String.format("onPlayerDeathEvent: %s -> %s", killed, killer);

            Team killerTeam = new Team(killer.getTeamId());

            killer.setScore(killer.getScore() + killed.getBounty());
            killer.setBounty(killer.getBounty() + 1);
            killerTeam.setScore(killerTeam.getScore() + killed.getBounty());
            killed.setBounty(0);

            Scoreboard killerScoreboard = new Scoreboard(killer);
            killerScoreboard.setScore(killer.getScore());
            killerScoreboard.setTeamScore(killer.getUuid(), killerTeam.getScore());
        }
    }
}
