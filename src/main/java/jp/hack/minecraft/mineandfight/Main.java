package jp.hack.minecraft.mineandfight;

import jp.hack.minecraft.mineandfight.core.GamePlugin;
import jp.hack.minecraft.mineandfight.logic.MineAndFightCommand;

public final class Main extends GamePlugin {

    @Override
    public void onEnable() {
        super.onEnable();

        getCommand("mineandfight").setExecutor(new MineAndFightCommand(this));
    }
}
