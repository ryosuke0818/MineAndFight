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

import java.util.ArrayList;
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
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        if (true) {
            Player killed = game.findPlayer(event.getEntity().getUniqueId());

            if (event.getEntity().getKiller() instanceof org.bukkit.entity.Player) {
                Player killer = game.findPlayer(event.getEntity().getKiller().getUniqueId());
                LOGGER.info(String.format("onPlayerDeathEvent: %s -> %s", event.getEntity().getName(), event.getEntity().getKiller().getName()));

                Team killerTeam = new Team(killer.getTeamId());

                killer.setScore(killer.getScore() + killed.getBounty());
                killer.setBounty(killer.getBounty() + 1);
                killerTeam.setScore(killerTeam.getScore() + killed.getBounty());
                killed.setBounty(0);

                ArrayList<Player> teamMate = (ArrayList<Player>) game.getTeamPlayers(killerTeam.getTeamId());
                for (int i = 0; i < teamMate.size(); i++) {
                    Scoreboard playerScoreboard = new Scoreboard(teamMate.get(i).getUuid());
                    playerScoreboard.setScore(teamMate.get(i).getScore());
                    //playerScoreboard.setTeamScore(killerTeam.getScore());
                    playerScoreboard.setScoreboard();
                }
            } else {
            }
        }
    }

    public void onStart() {

    }
}
