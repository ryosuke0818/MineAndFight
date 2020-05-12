package jp.hack.minecraft.mineandfight.core;

import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

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
        for(Iterator<Game> ite=games.values().iterator(); ite.hasNext();){
            Game g = ite.next();
            if(g.findPlayer(event.getPlayer().getUniqueId())!=null) {
                g.onLogin(event);
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerMoveEvent​(PlayerMoveEvent event​) {
        //ゲームエリア外にでた場合は処理をキャンセルする
        for(Iterator<Game> ite=games.values().iterator(); ite.hasNext();) {
            Game g = ite.next();
            if(g.findPlayer(event​.getPlayer().getUniqueId())!=null){
                if(g.getGameArea().contains(event​.getTo().toVector()) != true){
                    event​.setCancelled(true);
                    break;
                }
            }
        }
    }


    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        Location loc = event.getBlock().getLocation();
        for(Iterator<Game> ite=games.values().iterator(); ite.hasNext();) {
            Game g = ite.next();
            if(g.getGameArea().contains(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ())) {
                g.onBlockBreakEvent(event);
                break;
            }
        }
    }

    @EventHandler
    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        for(Iterator<Game> ite=games.values().iterator(); ite.hasNext();){
            Game g = ite.next();
            if(g.findPlayer(event.getEntity().getUniqueId())!=null) {
                g.onPlayerDeathEvent(event);
                break;
            }
        }
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
