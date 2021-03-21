package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.CreatureSpawnEvent

class CreatureSpawnListener : Listener {

    @EventHandler
    fun onCreatureSpawn(e: CreatureSpawnEvent) {
        if (e.location.world.name == "ASkyBlock_nether" && e.entity.type == EntityType.VILLAGER) {
            e.isCancelled = true

        }
        if (e.location.world == SkyLands.WORLD_SKYPVP && e.entity.type == EntityType.CHICKEN) {
            e.isCancelled = true
        }
    }

}