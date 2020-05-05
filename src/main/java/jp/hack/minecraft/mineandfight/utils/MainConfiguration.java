package jp.hack.minecraft.mineandfight.utils;

import jp.hack.minecraft.mineandfight.core.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.material.Wool;

import java.io.File;

public class MainConfiguration extends Configuration {
    public MainConfiguration(File configFile) {
        super(configFile);
        setTemplateName("config.yml");



    }
}
