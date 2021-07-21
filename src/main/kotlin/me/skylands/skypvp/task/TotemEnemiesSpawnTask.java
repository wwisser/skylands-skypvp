package me.skylands.skypvp.task;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.Totem;
import org.bukkit.scheduler.BukkitRunnable;

public class TotemEnemiesSpawnTask extends BukkitRunnable {

    @Override
    public void run() {
        for (Totem totem : SkyLands.totemConfig.getTotems().values()) {
            totem.spawnMobs();
        }
    }
}
