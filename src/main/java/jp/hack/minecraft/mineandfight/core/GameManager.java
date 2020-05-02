package jp.hack.minecraft.mineandfight.core;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

public class GameManager {
    private static class SingletonHolder {
        private static final AtomicInteger ids = new AtomicInteger();
        private static final GameManager singleton = new GameManager();
        private SingletonHolder() { }
    }
    public static synchronized GameManager getInstance(){
        return SingletonHolder.singleton;
    }

    private final ExecutorService pool;
    private Map<Game, Future> games = new HashMap<>();

    private GameManager(){
        pool = Executors.newCachedThreadPool(runnable -> {
            Thread thread = new Thread(runnable, "game thread " + SingletonHolder.ids.incrementAndGet());
            thread.setDaemon(true);
            return thread;
        });
    }


    public void start(Game game){
        Future future = pool.submit(game);
        games.put(game, future);
        game.onStart();
    }

    public void stop(Game game){
        Future future = games.remove(game);
        if(future!=null){
            future.cancel(true);
            game.onStop();
        }
    }

}
