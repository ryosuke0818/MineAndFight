package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.command.HostCommandExecutor;
import jp.hack.minecraft.mineandfight.command.PlayerCommandExecutor;
import jp.hack.minecraft.mineandfight.core.Game;
import jp.hack.minecraft.mineandfight.logic.MineAndFight;
<<<<<<< HEAD
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
=======
import jp.hack.minecraft.mineandfight.utils.Configuration;
import jp.hack.minecraft.mineandfight.utils.I18n;
>>>>>>> 0e925faa164ec2ab829eae566c1777e3dda4caa4
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    private transient Game game = new Game(this);
    private transient Configuration configuration;
    private transient I18n i18n;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new MineAndFight(game), this);

        getCommand("mineandfight").setExecutor(new HostCommandExecutor(game));
        getCommand("game").setExecutor(new PlayerCommandExecutor(game));

        this.i18n = new I18n(this);
        this.i18n.onEnable();
        this.i18n.updateLocale("ja");

        this.configuration = new Configuration(getDataFolder());
        this.configuration.setTemplateName("config.yml");
        this.configuration.load();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.i18n != null) {
            this.i18n.onDisable();
        }
    }
}
