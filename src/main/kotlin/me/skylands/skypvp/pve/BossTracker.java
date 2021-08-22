package me.skylands.skypvp.pve;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class BossTracker {

    public CraftEntity getBossHandle(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        return bossData.getBossHandle();
    }

    public boolean isBossAlive(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        if(bossData == null) {
            return false;
        } else {
            return bossData.isAlive();
        }
    }

    public Location getBossLocation(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        return bossData.getBossHandle().getLocation();
    }

    public void teleportBossToTotem(int bossID) {
        BossData bossData = Helper.bossData.get(bossID);

        bossData.getBossHandle().teleport(bossData.getSpawnLocation());
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
}
