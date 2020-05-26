package jp.hack.minecraft.mineandfight.command.subCommand;

import jp.hack.minecraft.mineandfight.core.GameCommandExecutor;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
public class HostSubCommand extends GameCommandExecutor {
    public HostSubCommand(GamePlugin plugin) {
        super(plugin);
        addSubCommand(new GameStartCommand(plugin));
        addSubCommand(new GameCreateCommand(plugin));
        addSubCommand(new GameDeleteCommand(plugin));
        addSubCommand(new GameListCommand(plugin));
    }

    @Override
    public String getName() {
        return "host";
    }

    @Override
    public String getPermission() {
        return null;
    }

}
