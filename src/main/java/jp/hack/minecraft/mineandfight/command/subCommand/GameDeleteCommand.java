package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.MainConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameDeleteCommand implements SubCommand {
    GamePlugin plugin;

    public GameDeleteCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "delete";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 1){
            sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
            return false;
        }
        String gameId = args[0];

        GameManager gameManager = GameManager.getInstance();
        if (gameManager.getGameNames().contains(gameId)) {
            MainConfiguration mainConfiguration = new MainConfiguration(new File("resources/config.yml"));


            gameManager.deleteGame(gameId);
            mainConfiguration.deleteGame(gameId);

            sender.sendMessage(ChatColor.GREEN + "Succeed: You deleted " + gameId + ".");
        } else {
            sender.sendMessage(I18n.tl("error.command.uncreated.game"));
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        GameManager gameManager = GameManager.getInstance();
        if(args.length < 2) return gameManager.getGameNames();
        else                return new ArrayList<>();
    }
}
