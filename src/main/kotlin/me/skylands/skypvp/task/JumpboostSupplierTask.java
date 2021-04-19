package me.skylands.skypvp.task;

import me.skylands.skypvp.SkyLands;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class JumpboostSupplierTask extends BukkitRunnable {

    private static final long PERIOD = 3L;
    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (!player.getWorld().equals(SkyLands.WORLD_SKYPVP)
                    || player.getLocation().clone().add(0, -1, 0).getBlock().getType() != Material.SLIME_BLOCK) {
                continue;
            }

            player.setVelocity(player.getLocation().getDirection().setY(0.3).multiply(2.8));
            player.playSound(player.getLocation(), Sound.SLIME_ATTACK, 1, 1);
        }
    }

}