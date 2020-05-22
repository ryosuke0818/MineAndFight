package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.logic.Effect;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public class TestSubCommand  implements SubCommand {
    @Override
    public String getName() {
        return "test";
    }

    @Override
    public String getPermission() {
        return null;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args[0] == "test1") {
            Player player = (Player) sender;
            sender.sendMessage("TestCommand");
            Effect.TEST(player);
            return true;
        }
        if(args[0] == "title"){
            getLogger().info("Ready.");
            for(Player player : Bukkit.getOnlinePlayers()){
                player.sendTitle("Start!","ゲームが開始されました!",10,70,20);
            }
            return true;
            // コマンドが実行された場合は、trueを返して当メソッドを抜ける。
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<String>(Arrays.asList("test1","title"));
    }
}
