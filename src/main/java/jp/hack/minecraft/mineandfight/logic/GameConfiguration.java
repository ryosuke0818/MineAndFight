package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.utils.Configuration;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class GameConfiguration extends Configuration{

    public GameConfiguration(File configFile) {
        super(configFile);
    }

    public static GameConfiguration create(GamePlugin plugin, String gameId){
        File gameFolder = new File(plugin.getDataFolder(), gameId);
        if(!gameFolder.exists()){
            gameFolder.mkdirs();
        }

        GameConfiguration configuration = new GameConfiguration(new File(gameFolder, "config.yml"));
        configuration.load();

        return configuration;
    }

    public void setPos1(Vector v){
        set("pos1",
                new Vector(v.getX(), v.getY(), v.getZ()));
    }

    public Vector getPos1(){
        return (Vector) get("pos1");
    }

    public void setPos2(Vector v){
        set("pos2",
                new Vector(v.getX(), v.getY(), v.getZ()));
    }

    public Vector getPos2(){
        return (Vector) get("pos2");
    }

    public void setSchem(String fname) {
        set("schem", fname);
    }

    public String getSchem(){
        return getString("schem");
    }



}
