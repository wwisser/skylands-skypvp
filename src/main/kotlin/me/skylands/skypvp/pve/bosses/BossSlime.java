package me.skylands.skypvp.pve.bosses;
import me.skylands.skypvp.pve.bosses.attacks.BossAttack;
import me.skylands.skypvp.pve.bosses.attacks.ExplosionAttack;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

public class BossSlime extends EntitySlime implements Boss {

    public BossSlime(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(this.getBossHealth());
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(this.getFollowRange());
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(GenericAttributes.MOVEMENT_SPEED.b() * this.attackSpeedMultiplier());
        this.setHealth(this.getBossHealth());
        this.setSize(5);

        this.setCustomName(this.getBossPrefix() + "§a§lSLIME");
        this.setCustomNameVisible(true);

        this.targetSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
        this.targetSelector.a(1, new PathfinderGoalTargetNearestPlayer(this));


        this.getWorld().addEntity(this);
    }

    @Override
    public BossAttack getBossAttack() {
        return new ExplosionAttack();
    }
}
