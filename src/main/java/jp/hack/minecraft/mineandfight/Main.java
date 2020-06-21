package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.core.GameManager;
import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.command.MineAndFightCommand;
import jp.hack.minecraft.mineandfight.logic.MineAndFightLogic;

public final class Main extends GamePlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("mineandfight").setExecutor(new MineAndFightCommand(this));

        GameManager.getInstance().setGenerator((plugin, gameID) -> new MineAndFightLogic(plugin, gameID));

        GameManager.getInstance().loadGame(this);
    }

    @Override
    public void onDisable(){
        super.onDisable();

        GameManager.getInstance().release();
    }
}
