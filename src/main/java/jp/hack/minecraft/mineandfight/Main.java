package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.command.HostCommandExecutor;
import jp.hack.minecraft.mineandfight.command.PlayerCommandExecutor;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("mineandfight").setExecutor(new HostCommandExecutor(this));
        getCommand("game").setExecutor(new PlayerCommandExecutor(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
