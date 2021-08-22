package me.skylands.skypvp.task.pve;

import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;

public class BossParticleTask extends BukkitRunnable {

    private int timePassed = 0;
    private CraftEntity boss;
    private final BossTracker bossTracker = new BossTracker();

    public BossParticleTask(int bossID) {
        this.boss = this.bossTracker.getBossHandle(bossID);
    }

    @Override
    public void run() {

        /**
         *  TODO
         */

        this.timePassed++;
    }
}
