package jp.hack.minecraft.mineandfight.logic;

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
import com.sk89q.worldedit.world.World;
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

        org.bukkit.entity.Player player = (org.bukkit.entity.Player)sender;

        if(subCommand.equals("create")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];

            return saveStatg(player, gameId);
        }
        else if(subCommand.equals("start")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];

            com.sk89q.worldedit.bukkit.BukkitPlayer wePlayer = BukkitAdapter.adapt(player);

            boolean ret = loadStage(wePlayer.getWorld(), gameId);
            if(ret){
                //TODO マップ読込後の処理
                return true;
            }
        }

        return false;

    }

    private boolean saveStatg(org.bukkit.entity.Player player, String gameId){
        com.sk89q.worldedit.bukkit.BukkitPlayer wePlayer = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().get(wePlayer);
        try {
            Region region = session.getRegionSelector(wePlayer.getWorld()).getRegion();

            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
            configuration.setPos1(new Vector(region.getMinimumPoint().getX(), region.getMinimumPoint().getY(), region.getMinimumPoint().getZ()));
            configuration.setPos2(new Vector(region.getMaximumPoint().getX(), region.getMaximumPoint().getY(), region.getMaximumPoint().getZ()));
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

    private boolean loadStage(World world, String gameId){
        GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
        LOGGER.info("stage file:"+configuration.getSchem());

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
                .getEditSession(world, -1)){
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
