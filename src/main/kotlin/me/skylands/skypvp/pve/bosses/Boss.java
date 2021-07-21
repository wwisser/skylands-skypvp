package me.skylands.skypvp.pve.bosses;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public abstract class Boss {

    EntityType entityType;
    Location location;
    double maxHealth;

    public Boss(EntityType entityType, double maxHealth, Location location) {
        this.entityType = entityType;
        this.maxHealth = maxHealth;
        this.location = location;
    }

    public abstract void spawnBoss();
}
