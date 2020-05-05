package jp.hack.minecraft.mineandfight.core;

import jp.hack.minecraft.mineandfight.core.utils.Configuration;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public abstract class GamePlugin extends JavaPlugin {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFightLogic");
    private transient Configuration configuration;
    private transient I18n i18n;
    private static Economy econ = null;

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(GameManager.getInstance(), this);

        if (getDataFolder().exists() != true) {
            getDataFolder().mkdirs();
        }

        this.i18n = new I18n(this);
        this.i18n.onEnable();
        this.i18n.updateLocale("ja");

        this.configuration = new Configuration(new File(getDataFolder(), "config.yml"));
        this.configuration.setTemplateName("config.yml");
        this.configuration.load();

        if (!setupEconomy() ) {
            LOGGER.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        if (this.configuration != null) {
            this.configuration.save();
        }

        if (this.i18n != null) {
            this.i18n.onDisable();
        }
    }

    public Economy getEconomy(){
        return econ;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public Configuration getConfiguration() {
        return configuration;
    }
}
