package jp.hack.minecraft.mineandfight.logic;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Random;

public class Effect {
    public static void TEST(Player player) {
        Random random = new Random();
        World world = player.getWorld();
        for (int i = 0; i < 100; i++) {
            world.spawnParticle(
                    Particle.VILLAGER_HAPPY, // パーティクルの種類
                    player.getLocation().getX() - 0.5 + random.nextDouble(),
                    player.getLocation().getY() + 1 + random.nextDouble(),
                    player.getLocation().getZ() - 0.5 + random.nextDouble(),// 発生させる場所
                    1 // 発生させる数
            );
        }
    }
}
