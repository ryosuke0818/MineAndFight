package jp.hack.minecraft.mineandfight.command;

import jp.hack.minecraft.mineandfight.core.GameCommandExecutor;
import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.core.utils.WorldEditorUtil;
import jp.hack.minecraft.mineandfight.logic.MineAndFightLogic;
import jp.hack.minecraft.mineandfight.core.utils.I18n;
import jp.hack.minecraft.mineandfight.utils.GameConfiguration;
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

        org.bukkit.entity.Player player = (org.bukkit.entity.Player)sender;

        if(subCommand.equals("create")) {
            if(args.length < 1){
                sender.sendMessage(I18n.tl("error.command.invalid.arguments"));
                return false;
            }
            String gameId = args[0];
            GameConfiguration configuration = GameConfiguration.create(plugin, gameId);

            return WorldEditorUtil.saveStage(player, configuration);
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

        return false;

    }
}
