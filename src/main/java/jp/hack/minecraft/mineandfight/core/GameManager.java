package jp.hack.minecraft.mineandfight.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.event.Listener;

public class GameManager implements Listener {
    private static class SingletonHolder {
        private static final AtomicInteger ids = new AtomicInteger();
        private static final GameManager singleton = new GameManager();
        private SingletonHolder() { }
    }
    public static synchronized GameManager getInstance(){
        return SingletonHolder.singleton;
    }

    private final ExecutorService pool;
    private Map<String, Game> games = new HashMap<>();

    private GameManager(){
        pool = Executors.newCachedThreadPool(runnable -> {
            Thread thread = new Thread(runnable, "game thread " + SingletonHolder.ids.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
    }

    public Game getGame(String id){
        return games.get(id);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        games.values().stream().forEach(g -> g.onLogin(event));
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        games.values().stream().forEach(g -> g.onBlockBreakEvent(event));
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        games.values().stream().forEach(g -> g.onPlayerDeathEvent(event));
    }


    public void start(Game game){
        if(!games.containsKey(game.getId())) {
            Future future = game.start(pool);
            if (future != null) {
                games.put(game.getId(), game);
            }
        }
    }

    public void stop(String id){
        Game game = remove(id);
        if(game!=null){
            game.cancel();
            game.onStop();
        }
    }

    Game remove(String id){
        return games.remove(id);
    }

}
