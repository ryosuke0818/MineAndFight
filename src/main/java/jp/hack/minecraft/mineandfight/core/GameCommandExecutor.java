package jp.hack.minecraft.mineandfight.core;

import jp.hack.minecraft.mineandfight.core.utils.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public abstract class GameCommandExecutor implements SubCommand {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFightLogic");
    protected final GamePlugin plugin;
    private Map<String, SubCommand> subCommands = new HashMap<>();

    public GameCommandExecutor(GamePlugin plugin) {
        this.plugin = plugin;
    }

    public List<String> getSubCommands() {
        return subCommands.keySet().stream().collect(Collectors.toList());
    }

    protected void addSubCommand(SubCommand subCommand){
        subCommands.put(subCommand.getName(), subCommand);
    }

    public boolean onCommandImpl(CommandSender sender, Command command, String label, String[] args){
        return false;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (getPermission() != null && !sender.hasPermission(getPermission())) {
            sender.sendMessage(I18n.tl("error.command.permission"));
            return false;
        }

        if (args.length < 1 || !getSubCommands().contains(args[0])) {
            return onCommandImpl(sender, command, label, args);
        }

        SubCommand subCommand = subCommands.get(args[0]);
        if (subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
            sender.sendMessage(I18n.tl("error.command.permission"));
            return false;
        }

        return subCommand.onCommand(sender, command, args[0], Arrays.copyOfRange(args, 1, args.length));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> commands = getSubCommands();

        if (args.length == 0 || args[0].length() == 0) {
            return commands;
        } else if (args.length == 1) {
            return commands.stream().filter(s->s.startsWith(args[0])).collect(Collectors.toList());
        } else {
            SubCommand subCommand = subCommands.get(args[0]);
            if(subCommand != null) return subCommand.onTabComplete(sender, command, args[0], Arrays.copyOfRange(args, 1, args.length));
        }
        return new ArrayList<>();
    }


}
