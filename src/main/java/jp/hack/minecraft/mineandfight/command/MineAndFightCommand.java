package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.core.*;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import jp.hack.minecraft.mineandfight.logic.MineAndFightLogic;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MineAndFightCommand extends GameCommandExecutor {
    private List<String> commands = new ArrayList(Arrays.asList("create", "start"));

    public MineAndFightCommand(GamePlugin plugin) {
        super(plugin);
    }

    @Override
    protected String getPermission() {
        return "mineandfight.host";
    }

    @Override
    protected List<String> getSubCommands() {
        return commands;
    }

    @Override
    public boolean onSubCommand(CommandSender sender, Command command, String subCommand, String[] args) {

        //Stageコマンド
        if(subCommand.equals("create")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            return WorldEditorUtil.saveStage((org.bukkit.entity.Player) sender, configuration);
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