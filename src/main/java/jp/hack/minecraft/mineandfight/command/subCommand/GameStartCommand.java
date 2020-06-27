package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GameStartCommand implements SubCommand {
    GamePlugin plugin;

    public GameStartCommand(GamePlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "start";
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
        if(GameManager.getInstance().isCreated(gameId)) {
            if(!GameManager.getInstance().isRunning(gameId)){
                GameManager.getInstance().start(gameId);
                return true;
            }else{
                sender.sendMessage(I18n.tl("error.command.started.game", gameId));
            }
        }else{
            sender.sendMessage(I18n.tl("error.command.uncreated.game", gameId));
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        GameManager gameManager = GameManager.getInstance();
        if(args.length < 3) return gameManager.getGameNames();
        else                return new ArrayList<>();
    }
}
