package me.skylands.skypvp.pve;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class BossData {

    private boolean isAlive;
    private CraftEntity bossHandle;
    private Location spawnLocation;

    public BossData(Boolean isAlive, CraftEntity bossHandle, Location spawnLocation) {
        this.isAlive = isAlive;
        this.bossHandle = bossHandle;
        this.spawnLocation = spawnLocation;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public CraftEntity getBossHandle() {
        return bossHandle;
    }

    public Location getSpawnLocation() {
        return spawnLocation;
    }
}
