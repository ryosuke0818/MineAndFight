package jp.hack.minecraft.mineandfight.logic;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class Effect {
  public static void TEST(Player player) {
    World world = player.getWorld();
    world.spawnParticle(
      Particle.VILLAGER_HAPPY, // パーティクルの種類
      player.getLocation(), // 発生させる場所
      10 // 発生させる数
    );
  }
}
