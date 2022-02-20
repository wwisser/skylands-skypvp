package me.skylands.skypvp.pve.data;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class BossData {

    private boolean isAlive;

    private CraftEntity bossHandle;
    private Location randomSpawnLocation;
    private Location totemCenterLocation;


    public BossData(Boolean isAlive, CraftEntity bossHandle, Location randomSpawnLocation, Location totemCenterLocation) {
        this.isAlive = isAlive;
        this.bossHandle = bossHandle;
        this.randomSpawnLocation = randomSpawnLocation;
        this.totemCenterLocation = totemCenterLocation;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public CraftEntity getBossHandle() {
        return bossHandle;
    }

    public Location getRandomSpawnLocation() {
        return randomSpawnLocation;
    }

    public Location getTotemCenterLocation() {
        return totemCenterLocation;
    }
}
