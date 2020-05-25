package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.command.subCommand.*;
import jp.hack.minecraft.mineandfight.core.*;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import jp.hack.minecraft.mineandfight.logic.MineAndFightLogic;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class MineAndFightCommand extends GameCommandExecutor {

    public MineAndFightCommand(GamePlugin plugin) {
        super(plugin);

        addSubCommand(new HostSubCommand(plugin));
        addSubCommand(new JoinSubCommand(plugin));
        addSubCommand(new LeaveSubCommand(plugin));
        addSubCommand(new ListSubCommand(plugin));
    }

    @Override
    public String getName() {
        return "maf";
    }

    @Override
    public String getPermission() {
        return "mineandfight.host";
    }
}
