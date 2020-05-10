package jp.hack.minecraft.mineandfight.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.List;

public interface SubCommand extends TabExecutor {
    String getName();
    String getPermission();
}
