package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.GamePlugin;

import java.util.HashMap;
import java.util.Map;

public class MineAndFightManager {
    GamePlugin plugin;
    Map<String, MineAndFightLogic> games = new HashMap<>();

    public MineAndFightManager(GamePlugin plugin){
        plugin.getConfiguration().getGameList().stream().forEach(gameId->{
            games.put(gameId, new MineAndFightLogic(plugin, gameId));
        });
    }

    public void reload(){
        plugin.getConfiguration().getGameList().stream().forEach(gameId->{
            if(!games.containsKey(gameId)) {
                games.put(gameId, new MineAndFightLogic(plugin, gameId));
            }
        });
    }

    public MineAndFightLogic getLogic(String gameId){
        return games.get(gameId);
    }
}
