package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.Scoreboard;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Team;

import java.util.List;

public class HostCommandExecutor implements CommandExecutor, TabExecutor {
    private final JavaPlugin plugin;
    Scoreboard scoreboard;

    public HostCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;

        if(player.isOp()) {
            switch (args[0]) {
                case "game":
                    try {
                        int gameNumber = Integer.parseInt(args[1]);
                    }catch (NumberFormatException e){
                        return false;
                    }
                    //ゲーム番号を取得

                    switch (args[2]) {

                        case "start":

                            GameManager gameManager = GameManager.getInstance();
                            break;

                        case "add":

                            String teamName = args[3];
                            String playerName = args[4];

                            if(teamName.equals(null) || playerName.equals(null)){
                                return false;
                            }
                            break;

                        case "addTeam":

                            
                            break;

                        case "create":

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
