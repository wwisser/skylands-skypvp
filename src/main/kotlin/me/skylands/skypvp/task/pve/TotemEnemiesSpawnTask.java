package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.config.TotemConfig;
import me.skylands.skypvp.pve.BossData;
import me.skylands.skypvp.pve.BossTracker;
import me.skylands.skypvp.pve.Helper;
import me.skylands.skypvp.pve.Totem;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ThreadLocalRandom;

public class TotemEnemiesSpawnTask extends BukkitRunnable {

    private final BossTracker bossTracker = new BossTracker();
    private final TotemConfig totemConfig = SkyLands.totemConfig;

    private int timePassed = 0;

    @Override
    public void run() {

        if (this.timePassed % 30 == 0) {    // Spawn totem mobs every 30 seconds
            this.spawnTotemMobs();
        }

        if (this.timePassed / 60 == 1) {   // Spawn boss every five minutes (for testing purposes: 1 minute)
            // TODO SPAWN CONDITIONS (players online, etc.)
            this.spawnBoss();
            this.timePassed = 0;
        }
        this.timePassed = this.timePassed + 1;
    }


    private void spawnTotemMobs() {
        for (Totem totem : totemConfig.getTotems().values()) {
            totem.spawnMobs();
        }
    }

    private void spawnBoss() {
        for (Totem totem : totemConfig.getTotems().values()) {
            if (!this.bossTracker.isBossAlive(totem.getTotemIdentifier())) {
                totem.spawnBoss();
            }
        }
    }
}