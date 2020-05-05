package jp.hack.minecraft.mineandfight.core;

import jp.hack.minecraft.mineandfight.core.utils.Threading;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.util.BoundingBox;

import java.util.*;
import java.util.concurrent.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;


public abstract class Game implements Runnable {
    protected static final Logger LOGGER = Logger.getLogger("MineAndFightLogic");
    private final GamePlugin plugin;
    private String id;
    private Map<Integer, Team> teams = new ConcurrentHashMap<>();
    private Map<UUID, Player> players = new ConcurrentHashMap<>();
    private Future future;
    private transient boolean isFinish = false;
    protected GameConfiguration configuration;
    private transient BoundingBox gameArea;

    public Game(GamePlugin plugin, String id){
        this.plugin = plugin;
        this.id = id;
        this.configuration = GameConfiguration.create(plugin, id);
    }

    public String getId(){
        return id;
    }

    Future start(ExecutorService pool){
        this.future = pool.submit(this);
        return future;
    }

    void cancel(){
        if(this.future!=null){
            this.future.cancel(true);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public GamePlugin getPlugin() {
        return plugin;
    }

    public Collection<Player> getJoinPlayers(){
        return players.values();
    }

    public Collection<Player> getTeamPlayers(int teamId){
        return players.values().stream()
                .filter(player -> player.getTeamId() == teamId).collect(Collectors.toList());
    }

    public Player findPlayer(UUID uuid){
        return players.get(uuid);
    }

    public Collection<Team> getTeams(){
        return teams.values();
    }

    public Team getTeam(int teamId){
        return teams.get(teamId);
    }

    public void setTeam(Team team, int teamId){
        teams.put(teamId, team);
    }

    public int getTeamScore(int teamId){
        int playerScoreSum =  players.values().stream().filter(p->p.getTeamId() == teamId).mapToInt(p->p.getScore()).sum();
        int teamScore = getTeam(teamId).getScore();
        return  playerScoreSum + teamScore;
    }

    public void onLogin(PlayerJoinEvent event){
        LOGGER.info(String.format("onLogin: %s", event.getPlayer().getName()));
    }

    public void onBlockBreakEvent(BlockBreakEvent event) {
        LOGGER.info(String.format("onBlockBreakEvent: %s", event.getPlayer().getName()));
    }

    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        LOGGER.info(String.format("onPlayerDeathEvent: %s -> %s", event.getEntity().getName(), event.getEntity().getKiller().getName()));
    }

    @Override
    public void run() {
        isFinish = false;
        try {
            Threading.ensureServerThread(getPlugin(), ()->onStart());

            long startTime = System.currentTimeMillis();
            long currentTime = startTime;
            while (!isFinish) {
                final long dt = currentTime - startTime;
                boolean ret = Threading.postToServerThread(plugin, ()->onTask(dt)).get();
                if(ret != true) break;
                Thread.sleep(1000);
                currentTime = System.currentTimeMillis();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            isFinish = true;
            Threading.ensureServerThread(getPlugin(), ()->onEnd());
            GameManager.getInstance().remove(getId());
        }
    }

    public synchronized BoundingBox getGameArea(){
        if(gameArea == null) {
            gameArea = BoundingBox.of(configuration.getPos1(), configuration.getPos2());
        }
        return gameArea;
    }

    abstract public void onStart();
    abstract public void onStop();
    abstract public void onEnd();
    abstract public boolean onTask(long dt);
}
