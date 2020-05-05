package jp.hack.minecraft.mineandfight.utils;

import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.utils.Configuration;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.io.File;
import java.util.List;

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

        plugin.getConfiguration().addGame(gameId);

        return configuration;
    }

    public boolean isCreated(){
        return getSchem() != null;
    }

    public void setPos1(Vector v){
        set("pos1", v);
    }

    public Vector getPos1(){
        return (Vector) get("pos1");
    }

    public void setPos2(Vector v){
        set("pos2", v);
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


    public void setOrigin(Location location) {
        set("origin", location);
    }

    public Location getOrigin(){
        return (Location)get("origin");
    }
}
