package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LeaveSubCommand implements SubCommand {
    GamePlugin plugin;

    public LeaveSubCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        GameManager gameManager = GameManager.getInstance();

        Game game = gameManager.findGame(player.getUniqueId());
        if(game!=null){
            //ゲームから抜ける
            game.removePlayer(player.getUniqueId());
            sender.sendMessage(ChatColor.GREEN +"Successed: You left " + game.getId() + ".");
        } else {
            sender.sendMessage(ChatColor.RED +"Error: No such game.");
            return false;
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
