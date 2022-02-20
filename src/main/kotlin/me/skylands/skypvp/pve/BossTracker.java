package me.skylands.skypvp.pve;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import me.skylands.skypvp.SkyLands;
import me.skylands.skypvp.pve.bosses.Boss;
import me.skylands.skypvp.pve.bosses.BossSlime;
import me.skylands.skypvp.pve.data.BossData;
import me.skylands.skypvp.pve.data.CacheData;
import me.skylands.skypvp.pve.data.StatsData;
import me.skylands.skypvp.task.pve.BossParticleTask;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BossTracker {

    public static HashMap<Integer, BossParticleTask> runningParticleTasks = new HashMap<Integer, BossParticleTask>();
    private final Helper helper = new Helper();

    public void initBoss(int bossID, Boss boss) {
        CraftEntity bossHandle = getBossHandle(bossID);
        if (boss instanceof BossSlime) {
            ((Slime) bossHandle).addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 3, false, false));
            ((Slime) bossHandle).addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 3, false, false));
            Bukkit.getLogger().info("Max Health Of Boss Is " + ((BossSlime) boss).getMaxHealth());
        }
        helper.setDataStatus(0);
    }

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

    public void createBossHologram(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);
        Location totemCenterLoc = bossData.getTotemCenterLocation();
        Location holoLoc = new Location(SkyLands.WORLD_SKYPVP, totemCenterLoc.getX(),totemCenterLoc.getY() + 3,totemCenterLoc.getZ());

        Hologram hologram = HologramsAPI.createHologram(SkyLands.plugin, holoLoc);
        hologram.appendTextLine("LOADING..");
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
            bossParticleTask.runTaskTimer(SkyLands.plugin, 0,10);

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
