package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameCreateCommand implements SubCommand {
    GamePlugin plugin;

    public GameCreateCommand(GamePlugin plugin) {
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
        return createGame(gameId, (org.bukkit.entity.Player) sender);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return new ArrayList<>();
    }

    public boolean createGame(String gameId, org.bukkit.entity.Player player){
        Game game = GameManager.getInstance().createGame(plugin, gameId);
        if(game!=null) {
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
            boolean ret = WorldEditorUtil.saveStage(player, configuration);
            if (ret) {
                BoundingBox box = BoundingBox.of(configuration.getPos1(), configuration.getPos2());
                box = box.expand(-1, -1, -1);
                Vector max = box.getMax();
                Vector min = box.getMin();
                double y = min.getY() + (max.getY() - min.getY()) / 2;
                Vector p1 = new Vector(max.getX(), y, max.getZ());
                Vector p2 = new Vector(max.getX(), y, min.getZ());
                Vector p3 = new Vector(min.getX(), y, max.getZ());
                Vector p4 = new Vector(min.getX(), y, min.getZ());
                List<Vector> respawns = Arrays.asList(p1, p2, p3, p4);
                configuration.setRespawns(respawns);
                configuration.save();

<<<<<<< HEAD
            sender.sendMessage(ChatColor.GREEN +"Successed!");
            return true;
=======

                return true;
            }
>>>>>>> 8ceee5c0d58a2250a28904569a05a04757e48b04
        }
        sender.sendMessage(ChatColor.RED+"You must set positions!");
        return false;
    }

}
