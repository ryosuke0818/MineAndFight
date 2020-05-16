package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.command.subCommand.GameSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.PlayerSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.TeamSubCommand;
import jp.hack.minecraft.mineandfight.command.subCommand.TestSubCommand;
import jp.hack.minecraft.mineandfight.core.*;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import jp.hack.minecraft.mineandfight.logic.MineAndFightLogic;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Collectors;

public class MineAndFightCommand extends GameCommandExecutor {

    public MineAndFightCommand(GamePlugin plugin) {
        super(plugin);

        addSubCommand(new GameSubCommand(plugin));
        addSubCommand(new PlayerSubCommand(plugin));
        addSubCommand(new TeamSubCommand(plugin));
        addSubCommand(new TestSubCommand());
    }

    @Override
    public String getName() {
        return "maf";
    }

    @Override
    public String getPermission() {
        return "mineandfight.host";
    }

    @Deprecated
    public boolean onSubCommand(CommandSender sender, Command command, String subCommand, String[] args) {

        //Stageコマンド
        if(subCommand.equals("create")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            boolean ret =  WorldEditorUtil.saveStage((org.bukkit.entity.Player) sender, configuration);
            if(ret){
                BoundingBox box = BoundingBox.of(configuration.getPos1(), configuration.getPos2());
                box = box.expand(-1, -1, -1);
                Vector max = box.getMax();
                Vector min = box.getMin();
                double y = min.getY() + (max.getY()-min.getY())/2;
                Vector p1 = new Vector(max.getX(), y, max.getZ());
                Vector p2 = new Vector(max.getX(), y, min.getZ());
                Vector p3 = new Vector(min.getX(), y, max.getZ());
                Vector p4 = new Vector(min.getX(), y, min.getZ());
                List<Vector> respawns = Arrays.asList(p1, p2, p3, p4);
                configuration.setRespawns(respawns);
                configuration.save();

                //指定された位置のブロックを空気に置き換える(サンプル）
                /*respawns.forEach(v -> {
                    Location loc = new Location(((org.bukkit.entity.Player) sender).getWorld(), v.getX(), v.getY(), v.getZ());
                    loc.getBlock().setType(Material.AIR);
                });*/

                return true;
            }
        }

        else if(subCommand.equals("start")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            if(configuration.isCreated()) {
                GameManager.getInstance().start(new MineAndFightLogic(plugin, gameId));
                return true;
            }else{
                sender.sendMessage(I18n.tl("error.command.uncreated.game", gameId));
            }
        }

        else if(subCommand.equals("delete")) {
            if (args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            if(configuration.isCreated()) {
                //GameManager.getInstance().delete();
                return true;
            }else{
                sender.sendMessage(I18n.tl("error.command.uncreated.game", gameId));
            }
        }

        else if(subCommand.equals("list")) {
            if (args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            if(configuration.isCreated()) {
                //GameManager.getInstance().list();
                return true;
            }else{
                sender.sendMessage(I18n.tl("error.command.uncreated.game", gameId));
            }
        }

        //Playerコマンド
        else if(subCommand.equals("player")) {
            if (args.length < 4){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String type = args[0];
            String playerName = args[1];
            int teamId = Integer.parseInt(args[2]);

            //Playerコマンド
            if(type.equals("add")) {
                String gameId = args[3];
                GameConfiguration configuration = GameConfiguration.create(plugin, gameId);
                GameManager gameManager = GameManager.getInstance();
                Game game = gameManager.getGame(gameId);

                if (configuration.isCreated()) {
                    Player player = new Player(Bukkit.getPlayer(playerName).getUniqueId());
                    player.setTeamId(teamId);
                    game.addPlayer(player);
                    return true;
                } else {
                    sender.sendMessage(I18n.tl("error.command.uncreated.game", gameId));
                }
            }

            else if(type.equals("delete")) {
                String gameId = args[3];

            }

            else if(type.equals("list")) {

            }
        }

        return false;
    }
}
