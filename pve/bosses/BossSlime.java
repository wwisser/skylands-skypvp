package me.skylands.skypvp.pve.bosses;
import me.skylands.skypvp.pve.bosses.pathfinders.PathfinderGoalReturnToTotem;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;


public class BossSlime extends EntitySlime implements Boss {

    public BossSlime(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(1024);
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(5);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(GenericAttributes.MOVEMENT_SPEED.b() * 4);
        this.setHealth(1024);
        this.setSize(5);

        this.setCustomName("§a§lSLIME");
        this.setCustomNameVisible(true);

        // this.targetSelector.a(0, new PathfinderGoalReturnToTotem(this, location, GenericAttributes.MOVEMENT_SPEED.b()));

        // CRAFTENTITY

        this.getWorld().addEntity(this);
    }
}
