package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.Player;
import jp.hack.minecraft.mineandfight.core.Scoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class HostCommandExecutor implements CommandExecutor, TabExecutor {
    private final JavaPlugin plugin;
    Scoreboard scoreboard;

    public HostCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        org.bukkit.entity.Player player = (org.bukkit.entity.Player) sender;

        if(player.isOp()) {

            switch (args[0]) {
                case "game":

                    String gameId;
                    GameManager gameManager = GameManager.getInstance();
                    Game game;

                    switch (args[1]) {

                        case "start":

                            gameId = args[2];

                            game = gameManager.getGame(gameId);
                            gameManager.start(game);
                            for (Player p : game.getJoinPlayers()) {
                                Scoreboard scoreboard = new Scoreboard(p.getUuid());
                            }
                            break;

                        case "create":

                            gameId = args[2];

                            game = gameManager.getGame(gameId);

                            break;

                        case "list":

                            
                            break;

                        default:
                            return false;
                    }

                    break;
                default:
                    return false;
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }
}
