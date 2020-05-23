package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.command.subCommand.GameSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.PlayerSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.TeamSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.TestSubCommand;
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

        addSubCommand(new GameSubCommand(plugin));
        addSubCommand(new PlayerSubCommand(plugin));
        addSubCommand(new TeamSubCommand(plugin));
        addSubCommand(new TestSubCommand());
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
