package jp.hack.minecraft.mineandfight.command;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import jp.hack.minecraft.mineandfight.core.Scoreboard;
import jp.hack.minecraft.mineandfight.utils.I18n;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;
import java.util.logging.Logger;

public class HostCommandExecutor implements CommandExecutor, TabExecutor {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFight");
    private final JavaPlugin plugin;
    Scoreboard scoreboard;

    public HostCommandExecutor(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!sender.hasPermission("mineandfight.host")){
            sender.sendMessage(I18n.tl("error.command.permission"));
            return false;
        }

        if(args.length < 1) {
            sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
            return false;
        }

        String subCommand = args[0];
        if(subCommand.equals("create")) {
            org.bukkit.entity.Player player = (org.bukkit.entity.Player)sender;
            com.sk89q.worldedit.bukkit.BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);
            try {
                Region region = session.getRegionSelector(wePlayer.getWorld()).getRegion();

                sender.sendMessage(String.format("%d,%d,%d - %d,%d,%d",
                        region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ(),
                        region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()
                        ));
            } catch (IncompleteRegionException e) {
                I18n.tl("error.command.invalid.region.incomplete");
                return false;
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> subCommands = new ArrayList(Arrays.asList("create"));

        if (args.length == 0 || args[0].length() == 0) {
            return subCommands;
        } else if(args.length == 1){
            for(String s : subCommands){
                if(s.startsWith(args[0])) return Collections.singletonList(s);
            }
        }

        return null;
    }

}
