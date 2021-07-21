package me.skylands.skypvp.listener;

import me.skylands.skypvp.SkyLands;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustEvent;

public class EntityCombustListener implements Listener {

    @EventHandler
    public void onEntityCombust(EntityCombustEvent event) {
        if (!event.getEntity().getWorld().equals(SkyLands.WORLD_SKYPVP) || event.getEntity() instanceof Player) return;
        event.setCancelled(true);
    }
}
