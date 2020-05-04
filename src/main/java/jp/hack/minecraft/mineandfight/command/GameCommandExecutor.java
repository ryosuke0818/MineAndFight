package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.utils.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public abstract class GameCommandExecutor implements CommandExecutor, TabExecutor {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFightLogic");
    protected final GamePlugin plugin;

    public GameCommandExecutor(GamePlugin plugin) {
        this.plugin = plugin;
    }

    protected abstract String getPermission();

    protected abstract List<String> getSubCommands();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage(I18n.tl("error.command.permission"));
            return false;
        }

        if (args.length < 1 || !getSubCommands().contains(args[0])) {
            sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
            return false;
        }

        return onSubCommand(sender, command, args[0], Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = getSubCommands();

        if (args.length == 0 || args[0].length() == 0) {
            return subCommands;
        } else if (args.length == 1) {
            for (String s : subCommands) {
                if (s.startsWith(args[0])) return Collections.singletonList(s);
            }
        }

        return null;
    }

    public abstract boolean onSubCommand(CommandSender sender, Command command, String subCommand, String[] args);


}
