package jp.hack.minecraft.mineandfight.core.utils;

import com.google.common.collect.Lists;
import com.sk89q.worldedit.*;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.BlockArrayClipboard;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.*;
import com.sk89q.worldedit.function.operation.ForwardExtentCopy;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.List;
import java.util.logging.Logger;

public class WorldEditorUtil {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFightLogic");

    public static boolean saveStage(org.bukkit.entity.Player player, GameConfiguration configuration){
        com.sk89q.worldedit.bukkit.BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);
        try {
            Region region = session.getRegionSelector(wePlayer.getWorld()).getRegion();

            configuration.setPos1(new Vector(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ()));
            configuration.setPos2(new Vector(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()));
            configuration.setOrigin(player.getLocation());
            configuration.setSchem("stage.schem");
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

            player.sendMessage("The clipboard has been copyed at " + vec.toString());
            List<String> messages = Lists.newArrayList();
            forwardExtentCopy.addStatusMessages(messages);
            messages.forEach(player::sendMessage);

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

        return false;
    }

    public static boolean loadStage(GameConfiguration configuration){
        Location location = configuration.getOrigin();
        com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(location.getWorld());

        Clipboard clipboard;
        File file = new File(configuration.getFile().getParent(), configuration.getSchem());

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
            LOGGER.info("clip.origin:" + clipboard.getOrigin());
            LOGGER.info("clip.region:" + clipboard.getRegion());
        } catch (FileNotFoundException e) {
            I18n.tl("error.worldedit.load", e);
            return false;
        } catch (IOException e) {
            I18n.tl("error.worldedit.load", e);
            return false;
        }

        try (EditSession editSession = WorldEdit.getInstance().getEditSessionFactory()
                .getEditSession(weWorld, -1)){
            Operation operation = new ClipboardHolder(clipboard)
                    .createPaste(editSession)
                    .to(clipboard.getOrigin())
                    .build();

            Operations.complete(operation);

            List<String> messages = Lists.newArrayList();
            messages.add("The clipboard has been pasted at " + clipboard.getOrigin().toString());
            operation.addStatusMessages(messages);
            messages.forEach(LOGGER::info);

            return true;
        } catch (MaxChangedBlocksException e) {
            I18n.tl("error.worldedit.load", e);
        } catch (WorldEditException e) {
            I18n.tl("error.worldedit.load", e);
        }

        return false;
    }
}
