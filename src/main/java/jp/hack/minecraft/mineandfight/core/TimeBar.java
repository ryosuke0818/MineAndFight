package jp.hack.minecraft.mineandfight.core;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.plugin.Plugin;

public class TimeBar {
    BossBar bossBar;

    public TimeBar(Plugin plugin) {
        bossBar = plugin.getServer().createBossBar("ボスヴァー", BarColor.YELLOW, BarStyle.SOLID);
        bossBar.setVisible(true);
        bossBar.setProgress(100.0);
    }

    public void setProgress(double percent) {
        if(!(percent > 100.0)) {
            bossBar.setProgress(100.0 - percent);
        } else {
            bossBar.setProgress(0.0);
        }
    }

    public void stop() {
        bossBar.removeAll();
    }
}
