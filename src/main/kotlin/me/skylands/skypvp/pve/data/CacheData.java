package me.skylands.skypvp.pve.data;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

public class CacheData {

    private final double damage;
    private final int consecutiveHits;

    public CacheData(Double damage, Integer consecutiveHits) {
        this.damage = damage;
        this.consecutiveHits = consecutiveHits;
    }

    public double getDamage() {
        return damage;
    }

    public int getConsecutiveHits() {
        return consecutiveHits;
    }
}