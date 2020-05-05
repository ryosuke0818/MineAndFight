package jp.hack.minecraft.mineandfight.utils;

import com.sun.media.jfxmedia.logging.Logger;
import jp.hack.minecraft.mineandfight.core.utils.Configuration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Wool;

import java.io.File;
import java.util.List;

public class MainConfiguration extends Configuration {

    public MainConfiguration(File configFile) {
        super(configFile);
        setTemplateName("config.yml");
        save();
    }


    public List<String> getGameList() {
        synchronized (this) {
            return getStringList("games");
        }
    }

    public void addGame(String gameId){
        synchronized (this) {
            List<String> list = getStringList("games");
            if(list.contains(gameId)!=true){
                list.add(gameId);
                setProperty("games", list);
            }
        }
    }

    public void deleteGame(String gameId){
        synchronized (this) {
            List<String> list = getStringList("games");
            if(list.contains(gameId)==true){
                list.remove(gameId);
                setProperty("games", list);
            }
        }
    }
}
