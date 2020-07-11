package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.MainConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class ListSubCommand implements SubCommand {
    GamePlugin plugin;

    public ListSubCommand(GamePlugin plugin){
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        MainConfiguration configuration = plugin.getConfiguration();
        List<String> games = configuration.getGameList();
        if (games.isEmpty()) {
            sender.sendMessage("ゲームはまだ生成されていません");
        } else {
            StringBuilder builder = new StringBuilder();

            games.stream().forEach(s -> builder.append(s).append(" "));
            sender.sendMessage(I18n.tl("message.command.list", builder.toString()));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
