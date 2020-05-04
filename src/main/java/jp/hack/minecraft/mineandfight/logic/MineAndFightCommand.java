package jp.hack.minecraft.mineandfight.logic;

import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.Extent;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import jp.hack.minecraft.mineandfight.command.GameCommandExecutor;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.utils.I18n;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MineAndFightCommand extends GameCommandExecutor {
    private List<String> commands = new ArrayList(Arrays.asList("create", "start"));

    public MineAndFightCommand(GamePlugin plugin) {
        super(plugin);
    }

    @Override
    protected String getPermission() {
        return "mineandfight.host";
    }

    @Override
    protected List<String> getSubCommands() {
        return commands;
    }

    @Override
    public boolean onSubCommand(CommandSender sender, Command command, String subCommand, String[] args) {

        if(subCommand.equals("create")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];

            org.bukkit.entity.Player player = (org.bukkit.entity.Player)sender;
            com.sk89q.worldedit.bukkit.BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
            LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);
            try {
                Region region = session.getRegionSelector(wePlayer.getWorld()).getRegion();

                sender.sendMessage(String.format("%d,%d,%d - %d,%d,%d",
                        region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ(),
                        region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()));

                GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
                configuration.setPos1(new Vector(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ()));
                configuration.setPos2(new Vector(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()));
                configuration.setOrigin(player.getLocation());
                configuration.setSchem(gameId + ".schem");
                configuration.save();

                BlockVector3 vec = session.getPlacementPosition(wePlayer);

                LOGGER.info("BlockVector3:"+vec.toString());
                LOGGER.info("location:"+player.getLocation().toString());

                BlockArrayClipboard clipboard = new BlockArrayClipboard(region);

                EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(wePlayer.getWorld(), -1);
                ForwardExtentCopy forwardExtentCopy = new ForwardExtentCopy(
                        editSession, region, clipboard, region.getMinimumPoint()
                );
                // configure here
                Operations.complete(forwardExtentCopy);

                File file = new File(configuration.getFile().getParent(), configuration.getSchem());

                ClipboardWriter writer = BuiltInClipboardFormat.SPONGE_SCHEMATIC.getWriter(new FileOutputStream(file));
                writer.write(clipboard);
                writer.close();

                return true;
            } catch (WorldEditException e) {
                I18n.tl("error.worldedit.save", e);
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                I18n.tl("error.worldedit.save", e);
                e.printStackTrace();
            } catch (IOException e) {
                I18n.tl("error.worldedit.save", e);
                e.printStackTrace();
            }
        }
        else if(subCommand.equals("start")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];

            org.bukkit.entity.Player player = (org.bukkit.entity.Player)sender;

            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
            Location origin = configuration.getOrigin();


            LOGGER.info("origin:"+origin);
            LOGGER.info("schem:"+configuration.getSchem());


            Clipboard clipboard;
            File file = new File(configuration.getFile().getParent(), configuration.getSchem());

            ClipboardFormat format = ClipboardFormats.findByFile(file);
            try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
                clipboard = reader.read();
                LOGGER.info("clip.origin:"+clipboard.getOrigin());
                LOGGER.info("clip.region:"+clipboard.getRegion());

                EditSession editSession = WorldEdit.getInstance().getEditSessionFactory().getEditSession(BukkitAdapter.adapt(origin.getWorld()), -1);
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(BukkitAdapter.asBlockVector(origin))
                        .ignoreAirBlocks(false)
                        .build();

                Operations.completeLegacy(operation);

                return true;
            } catch (FileNotFoundException e) {
                I18n.tl("error.worldedit.load", e);
            } catch (IOException e) {
                I18n.tl("error.worldedit.load", e);
            } catch (WorldEditException e) {
                I18n.tl("error.worldedit.load", e);
            }
        }


        return false;

    }

}
