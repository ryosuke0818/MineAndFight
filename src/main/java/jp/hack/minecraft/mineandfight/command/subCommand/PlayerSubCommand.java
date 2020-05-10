package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.GameCommandExecutor;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.SubCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.List;

public class PlayerSubCommand extends GameCommandExecutor {
    public PlayerSubCommand(GamePlugin plugin) {
        super(plugin);
    }

    @Override
    public String getName() {
        return "player";
    }

    @Override
    public String getPermission() {
        return null;
    }
}
