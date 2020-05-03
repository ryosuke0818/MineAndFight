package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.command.HostCommandExecutor;
import jp.hack.minecraft.mineandfight.command.PlayerCommandExecutor;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.utils.Configuration;
import jp.hack.minecraft.mineandfight.utils.I18n;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class Main extends JavaPlugin {
    private transient Configuration configuration;
    private transient I18n i18n;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(GameManager.getInstance(), this);

        getCommand("mineandfight").setExecutor(new HostCommandExecutor(this));
        getCommand("game").setExecutor(new PlayerCommandExecutor(this));

        if(getDataFolder().exists() != true){
            getDataFolder().mkdirs();
        }

        this.i18n = new I18n(this);
        this.i18n.onEnable();
        this.i18n.updateLocale("ja");

        this.configuration = new Configuration(new File(getDataFolder(), "config.yml"));
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
