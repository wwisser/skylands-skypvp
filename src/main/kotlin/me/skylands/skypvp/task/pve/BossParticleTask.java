package me.skylands.skypvp.task.pve;

import com.github.fierioziy.particlenativeapi.api.Particles_1_8;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.BossTracker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.scheduler.BukkitRunnable;


import java.util.Random;

public class BossParticleTask extends BukkitRunnable {

    private final BossTracker bossTracker = new BossTracker();
    private CraftEntity boss;
    private final Particles_1_8 particleHandle = SkyLands.particleAPI.getParticles_1_8();
    private int cancelMe = 1; // 0 = Cancel Task, 1 = Keep running, 2 = Cancel w/o pulse sequence
    public int secondsPassed = 0;

    public BossParticleTask(int bossID) {
        this.boss = this.bossTracker.getBossHandle(bossID);
    }

    @Override
    public void run() {

        if(cancelMe == 0) {
            this.cancel();
            this.cancelMe = 1;

            Bukkit.getScheduler().scheduleSyncRepeatingTask(SkyLands.plugin, () -> {
                if(secondsPassed <= 14) { // 7
                    pulseSequence(boss);
                    ++secondsPassed;
                } else {
                    cancel();
                }
            }, 0L, 15L);

            return;
        } else if (cancelMe == 2) {
            this.cancel();
            this.cancelMe = 1;

            return;
        }

        defaultParticles(boss);
    }


    private void pulseSequence(CraftEntity boss) {
        for (int i = 0; i < 50; i++) {
            double dx = Math.cos(Math.PI * 2 * ((double)i / 50)) * 0.75;
            double dz = Math.sin(Math.PI * 2 * ((double)i / 50)) * 0.75;
            double angle = Math.atan2(dz, dx);
            double xAng = Math.cos(angle);
            double zAng = Math.sin(angle);

            Random r = new Random();
            double rY = 0.2 + (3 - (0.2)) * r.nextDouble();

            Location currentLoc = boss.getLocation().clone().add(dx, rY, dz);
            Object object = particleHandle.FLAME().packetMotion(true, currentLoc, xAng, 0, zAng);
            particleHandle.sendPacket(currentLoc, 30, object);
        }
    }

    private void defaultParticles(CraftEntity boss) {
        for (int i = 0; i <= 1.9; i++) {
            for (double y = 0; y <= 6; y += 0.05) {
                double x = i * Math.cos(y);
                double z = i * Math.sin(y);

                Location bossLoc = boss.getLocation();
                Location pLoc = new Location(SkyLands.WORLD_SKYPVP, (float) (bossLoc.getX() + x), (float) (bossLoc.getY() + y), (float) (bossLoc.getZ() + z));
                Object object = particleHandle.ENCHANTMENT_TABLE().packetMotion(false, pLoc, 0D, 1D, 0D);
                particleHandle.sendPacket(pLoc, 30, object);
            }
        }
    }


    public void setCancelMe(int newCancelMe) {
        cancelMe = newCancelMe;
    }
}