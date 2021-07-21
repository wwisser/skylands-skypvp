package me.skylands.skypvp.pve.bosses;

import me.skylands.skypvp.SkyLands;
import net.minecraft.server.v1_8_R3.EntityGiantZombie;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.*;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.*;

public class BossGiant extends Boss {

    BossGiant() {
        super(EntityType.GIANT, 100, new Location(SkyLands.WORLD_SKYPVP, 1,1,1));
        Giant giant = Bukkit.getWorld("SkyPvP").spawn(location, Giant.class);
        giant.setMaxHealth(maxHealth);
        giant.setHealth(maxHealth);

    }

    @Override
    public void spawnBoss() {

    }
}
