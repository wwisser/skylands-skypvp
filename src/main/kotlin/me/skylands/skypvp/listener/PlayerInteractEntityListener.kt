package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.container.legacy.InventoryKit
import me.skylands.skypvp.container.legacy.InventoryTrade
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEntityEvent

class PlayerInteractEntityListener : Listener {

    @EventHandler
    fun onPlayerInteractEntity(ev: PlayerInteractEntityEvent) {
        val player = ev.player
        val entityType = ev.rightClicked.type
        if (player.world == SkyLands.WORLD_SKYPVP && player.location.blockY > SkyLands.getSpawnHeight()) {
            if (entityType == EntityType.WITCH) {
                player.performCommand("levelshop")
            }
            if (entityType == EntityType.ENDERMAN) {
                player.performCommand("is")
            }
            if (entityType == EntityType.SKELETON) {
                InventoryKit.open(player)
            }
            if (entityType == EntityType.VILLAGER) {
                InventoryTrade.open(player)
            }
            if(entityType == EntityType.COW) {
                player.performCommand("is warps")
            }

        }
    }

}