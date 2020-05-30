package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.command.MineAndFightCommand;
import jp.hack.minecraft.mineandfight.logic.MineAndFightManager;

public final class Main extends GamePlugin {
    MineAndFightManager mineAndFightManager;

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("mineandfight").setExecutor(new MineAndFightCommand(this));

        mineAndFightManager = new MineAndFightManager(this);
    }


    public MineAndFightManager getMineAndFightManager(){ return mineAndFightManager;}
}
