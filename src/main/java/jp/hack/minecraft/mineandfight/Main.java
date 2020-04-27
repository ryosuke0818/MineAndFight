package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.command.HostCommandExecutor;
import jp.hack.minecraft.mineandfight.command.PlayerCommandExecutor;
import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.logic.MineAndFight;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private Game game = new Game(this);

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MineAndFight(game), this);

        getCommand("mineandfight").setExecutor(new HostCommandExecutor(game));
        getCommand("game").setExecutor(new PlayerCommandExecutor(game));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
