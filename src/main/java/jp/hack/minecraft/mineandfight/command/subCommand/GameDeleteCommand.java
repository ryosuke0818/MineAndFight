package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GameDeleteCommand implements SubCommand {
    GamePlugin plugin;

    public GameDeleteCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "create";
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
        GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
        GameManager gameManager = GameManager.getInstance();

        if(configuration.isCreated()) {
            //GameをDeleteする
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
