package me.skylands.skypvp.listener.pve;

import me.skylands.skypvp.SkyLands;
import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

public class PvEMobListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if(!event.getEntity().getWorld().equals(SkyLands.WORLD_SKYPVP)) return;
        if(!(event.getDamager() instanceof Player) && event.getEntity() instanceof Player) {
            if(event.getDamager() instanceof Skeleton) {
                float damageMultiplier = 1.0f;

                if (event.getEntity().hasMetadata("100") & !(event.getEntityType() == EntityType.SLIME)) {
                    damageMultiplier = 5.0f;
                } else if (event.getEntity().hasMetadata("101")) {
                    damageMultiplier = 4.0f;
                } else if (event.getEntity().hasMetadata("102")) {
                    damageMultiplier = 6.0f;
                }
                event.setCancelled(true);
                ((Player) event.getEntity()).damage(damageMultiplier * 2);
            }
        }
    }
}
