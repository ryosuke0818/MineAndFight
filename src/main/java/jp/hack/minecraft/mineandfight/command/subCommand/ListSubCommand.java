package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ListSubCommand implements SubCommand {
    GamePlugin plugin;

    public ListSubCommand(GamePlugin plugin) {
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
        if(args.length < 1){
            sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
            return false;
        }
        Player player = (Player) sender;

        String gameId = args[0];
        GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
        GameManager gameManager = GameManager.getInstance();
        Game game = gameManager.getGame(gameId);
        StringBuilder builder = new StringBuilder();

        if(configuration.isCreated()) {
            game.getJoinPlayers().stream().forEach(p -> builder.append(p).append(" "));
            player.sendMessage(I18n.tl("message.command.list",builder.toString()));

            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }
}
