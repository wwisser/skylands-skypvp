package me.skylands.skypvp.pve.bosses;
import me.skylands.skypvp.pve.bosses.attacks.AreaMultiAttack;
import me.skylands.skypvp.pve.bosses.attacks.BossAttack;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.*;

import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.LivingEntity;


public class BossSlime extends EntitySlime implements Boss {

    public BossSlime(Location location) {
        super(((CraftWorld) location.getWorld()).getHandle());
        this.setPosition(location.getX(), location.getY(), location.getZ());

        this.getAttributeInstance(GenericAttributes.maxHealth).setValue(this.getBossHealth());
        this.getAttributeInstance(GenericAttributes.FOLLOW_RANGE).setValue(this.getFollowRange() * 2);
        this.getAttributeInstance(GenericAttributes.MOVEMENT_SPEED).setValue(GenericAttributes.MOVEMENT_SPEED.b() * this.attackSpeedMultiplier());
        this.setHealth(this.getBossHealth());
        this.setSize(5);

        this.setCustomName(this.getBossPrefix() + "§2§lSlimekönig");
        ((LivingEntity) this.getBukkitEntity()).setRemoveWhenFarAway(false);
        this.targetSelector.a(0, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));

        this.getWorld().addEntity(this);
    }

    @Override
    public BossAttack getBossAttack() {
        return new AreaMultiAttack();
    }
}
