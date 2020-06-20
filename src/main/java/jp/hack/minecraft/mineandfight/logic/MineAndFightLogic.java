package jp.hack.minecraft.mineandfight.logic;

import jp.hack.minecraft.mineandfight.core.*;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import org.bukkit.*;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.IntStream;

public class MineAndFightLogic extends Game implements Listener {

    private final String gameId;
    private GameManager gameManager = GameManager.getInstance();
    private Game game;
    public List<String> ranking;
    private Scoreboard scoreboard;
    private TimeBar timeBar;
    private long gametime = 1 * 1000 * 60;
    private final double MIN_EMERALDPERCENTAGE = 2;
    private final double MAX_EMERALDPERCENTAGE = 4;
    private final double BALANCE = 2.0;
    private final double ACCELERATE = 0.2;

    public MineAndFightLogic(GamePlugin plugin, String id) {
        super(plugin, id);
        gameId = id;
        scoreboard = new Scoreboard(id);
        game = gameManager.getGame(id);
        timeBar = new TimeBar(plugin);
    }

    public void onBlockBreakEvent(BlockBreakEvent event){
        Player breaker = findPlayer(event.getPlayer().getUniqueId());

        if(getJoinPlayers().contains(breaker)) {
            LOGGER.info(String.format("onBlockBreakEvent: %s", event.getPlayer().getName()));

            final String oreName = Material.EMERALD_ORE.data.getName();
            String blockName = event.getBlock().getBlockData().getMaterial().getData().getName();

            if (blockName.equals(oreName)) {
                breaker.setScore(breaker.getScore() + (breaker.getBounty() + 1));

                scoreboard.setScore(breaker.getName(), breaker.getScore());
            }
        }
    }

    public void onPlayerDeathEvent(PlayerDeathEvent event) {
        Player killed = findPlayer(event.getEntity().getUniqueId());

        if(getJoinPlayers().contains(killed)) {
            if (event.getEntity().getKiller() instanceof org.bukkit.entity.Player) {
                Player killer = findPlayer(event.getEntity().getKiller().getUniqueId());
                LOGGER.info(String.format("onPlayerDeathEvent: %s -> %s", event.getEntity().getName(), event.getEntity().getKiller().getName()));

                killer.setScore(killer.getScore() + killed.getBounty());
                killer.setBounty(killer.getBounty() + 1);

                killed.setBounty(0);

                scoreboard.setScore(killer.getName(), killer.getScore());
            }
        }
    }


    @Override
    public void onStart() {
        //TODO ゲームが開始されたら呼ばれます。

        WorldEditorUtil.loadStage(configuration);

        World world = Bukkit.getWorld("world");
        Vector minVec = configuration.getPos1();
        Vector maxVec = configuration.getPos2();

        List<List<Vector>> stones = Collections.synchronizedList(new ArrayList<>());

        //stoneの数を数える&各座標をy座標でフロアに分けて保存
        int stone_num=0;
        for(int y = minVec.getBlockY(); y <= maxVec.getBlockY(); y++){
            stones.add(new ArrayList<>());
            for(int x = minVec.getBlockX(); x <= maxVec.getBlockX(); x++){
                for(int z = minVec.getBlockZ(); z <= maxVec.getBlockZ(); z++){
                    if(world.getBlockAt(x,y,z).getType() == Material.STONE){
                        stone_num++;
                        stones.get(stones.size()-1).add(new Vector(x,y,z));
                    }
                }
            }
        }

        //エメラルドの数をstoneの数のMIN%以上MAX%未満にする
        int emerald_num = (int) (stone_num * ( new Random().nextDouble()*(MAX_EMERALDPERCENTAGE-MIN_EMERALDPERCENTAGE)+MIN_EMERALDPERCENTAGE ) / 100);

        //偏りすぎるのを防ぐ。BALANCEが小さいほど分散する。
        double balance_matched = BALANCE /(double) stones.size();

        //全体数が減ってbalance_matchedの力が強くなりすぎるのを徐々に抑える。ACCELERATEが小さい程抑える速度が遅くなる。
        final double accelerate_matched = ACCELERATE /(double) stones.size();

        //フロアごとのエメラルドの数を振り分ける
        ArrayList<Integer> floor_nums = new ArrayList<>();
        while(emerald_num>0 && floor_nums.size()<stones.size()-1){
            int num=new Random().nextInt((int)Math.round(emerald_num*balance_matched)+1);
            floor_nums.add(num);
            emerald_num-=num;

            balance_matched += accelerate_matched;
            //1は超えないようにする
            if(balance_matched>=1) balance_matched=1;
        }

        //残ったエメラルドを入れる
        floor_nums.add(emerald_num);

        //底の方が出やすくする為に並び替える
        floor_nums.sort(Collections.reverseOrder());

        //各フロアのランダムな位置をエメラルドに替える
        Iterator<Integer> em_itr = floor_nums.iterator();
        stones.stream().filter(list -> list.size()!=0 && em_itr.hasNext())
                        .forEach(list -> {
                            IntStream.range(0,em_itr.next())
                                    .forEach(i -> {
                                        Vector v=list.get(new Random().nextInt(list.size()));
                                        new Location(world,v.getBlockX(),v.getBlockY(),v.getBlockZ()).getBlock().setType(Material.EMERALD_ORE);
                                    });
                        });

        scoreboard = new Scoreboard(gameId);

        //TITLE
        getJoinPlayers().stream().forEach(p->{
            org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(p.getUuid());
            scoreboard.setScore(p.getName(), 0);
            scoreboard.setScoreboard(bukkitPlayer);

            double minY = Math.min(minVec.getY(), maxVec.getY()) + 1;
            Location location = new Location(configuration.getOrigin().getWorld(), minVec.getX(), minY, minVec.getZ(), 1, 0);
            bukkitPlayer.teleport(location);
            bukkitPlayer.sendTitle(ChatColor.GREEN +"Game Start", "", 1, 2, 1);
        });

        //プレイヤーを初期ポイントに移動する、四隅の初期値をランダムに選択しプレイヤーを移動する

        Bukkit.broadcastMessage("game start");

    }

    @Override
    public void onStop() {
        //TODO ゲームが停止されたら呼ばれます。

        getJoinPlayers().stream().forEach(p->{
            org.bukkit.entity.Player bukkitPlayer = Bukkit.getPlayer(p.getUuid());
            scoreboard.resetScoreboard(bukkitPlayer);
        });
    }

    @Override
    public void onEnd() {
        //TODO ゲームが終了したら呼ばれます。

        Map<String, Integer> scoreMap = new HashMap<>();
        List<String> players = new ArrayList<>();
        List<Integer> scores = new ArrayList<>();
        getJoinPlayers().stream().forEach(p->{
            scoreMap.put(p.getName(), p.getScore());
            players.add(p.getName());
            scores.add(p.getScore());
        });

        ranking = sort(players, scores);

        Bukkit.broadcastMessage("game stop");
    }

    @Override
    public boolean onTask(long dt) {
        //TODO　１秒単位に呼ばれる処理　Falseを返すとゲームは終了します。DTは経過時間（秒）
        System.out.println(dt);
        if(dt > gametime) {
            timeBar.setProgress((gametime/dt));
            return false;
        }
        return true;
    }

    public List<String> sort( List<String> list1, List<Integer> list2 ) {
        List<String> strList = list1;
        List<Integer> intList = list2;

        if (!(strList.size() == intList.size())) {
            for (int i = 0; i < intList.size() - 1; i++) {
                for (int j = 0; j < intList.size() - 1; j++) {
                    if(intList.get(j) < intList.get(j+1)) {
                        String strContent = strList.get(j);
                        int intContent = intList.get(j);

                        strList.set(j, strList.get(j+1));
                        strList.set(j+1, strContent);

                        intList.set(j, intList.get(j+1));
                        intList.set(j+1, intContent);
                    }
                }
            }
        }
        return strList;
    }

}
