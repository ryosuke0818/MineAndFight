package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GameSetTimeCommand implements SubCommand {
    GamePlugin plugin;

    public GameSetTimeCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "setTime";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length < 2){
            sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
            return false;
        }
        String gameId = args[0];
        long newTime;
        try {
            newTime = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            sender.sendMessage("Failed! Please input a number.");
            return false;
        }

        GameManager gameManager = GameManager.getInstance();
        Game game = gameManager.getGame(gameId);
        game.setGameTime(newTime * 1000);

        sender.sendMessage("Succeced!");

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        GameManager gameManager = GameManager.getInstance();
        if(args.length < 2) return gameManager.getGameNames();
        else                return new ArrayList<>();
    }
}
