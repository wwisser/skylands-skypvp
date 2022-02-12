package me.skylands.skypvp.pve;

import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.task.pve.BossParticleTask;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;

import java.util.HashMap;

public class BossTracker {

    public static HashMap<Integer, BossParticleTask> runningParticleTasks = new HashMap<Integer, BossParticleTask>();

    public boolean isValidID(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        return bossData != null;
    }

    public int getIDbyUUID(String uuid) {
        for(Integer bossID: Helper.bossData.keySet()) {
            CraftEntity handle = Helper.bossData.get(bossID).getBossHandle();

            if(handle.getUniqueId().toString().equals(uuid)) {
                return bossID;
            }
        }
        return 0;
    }

    public String getUUIDbyID(int bossID) {
        if(isValidID(bossID)) {
            CraftEntity handle = getBossHandle(bossID);
            return handle.getUniqueId().toString();
        }
        return null;
    }

    public CraftEntity getBossHandle(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        if(this.isBossAlive(bossID)) {
            return bossData.getBossHandle();
        }

        return null;
    }

    public boolean isBossAlive(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        if (bossData == null) {
            return false;
        }

        return bossData.isAlive();
    }

    public Location getBossLocation(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        return bossData.getBossHandle().getLocation();
    }

    public Location getTotemCenterLocation(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        return bossData.getTotemCenterLocation();
    }

    public void teleportBossToTotem(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        bossData.getBossHandle().teleport(bossData.getRandomSpawnLocation());
    }

    public void displayAttackParticles(int bossID) {
        if(!isParticleTaskRunning(bossID)) {
            BossParticleTask bossParticleTask = new BossParticleTask(bossID);
            bossParticleTask.runTaskTimer(SkyLands.plugin, 0,10); // 20 20

            runningParticleTasks.put(bossID, bossParticleTask);
        }
    }

    public BossParticleTask getParticleTask(int bossID) {
        BossParticleTask particleTask = null;
        if (runningParticleTasks.containsKey(bossID)) {
            particleTask = runningParticleTasks.get(bossID);

            if (this.isParticleTaskRunning(bossID)) {
                return particleTask;
            }
        }
        return null;
    }

    public boolean isParticleTaskRunning(int bossID) {
        return runningParticleTasks.containsKey(bossID);
    }

    public void cancelParticleTask(int bossID, boolean withPulseSequence) {
        if(isParticleTaskRunning(bossID)) {
            BossParticleTask particleTask = this.getParticleTask(bossID);

            if(withPulseSequence) {
                particleTask.setCancelMe(0);
            } else {
                particleTask.setCancelMe(2);
            }

            runningParticleTasks.remove(bossID);
        }
    }

    public void killBoss(int bossID, boolean silent) {
        World pveWorld = SkyLands.WORLD_SKYPVP;

        if(getUUIDbyID(bossID) == null) return;

        String uuid = getUUIDbyID(bossID);

        for(Entity entity: pveWorld.getEntities()) {
            if(entity.getUniqueId().toString().equals(uuid)) {
                if(entity instanceof Slime) {
                    if(silent) {
                        Helper.setSilentRemoval(true);
                        ((Slime) entity).damage(999999);
                        Helper.setSilentRemoval(false);
                    } else {
                        ((Slime) entity).damage(999999);
                    }
                }
            }
        }
    }
}
