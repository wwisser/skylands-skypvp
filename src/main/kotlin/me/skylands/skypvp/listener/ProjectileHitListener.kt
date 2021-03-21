package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent

class ProjectileHitListener : Listener {

    @EventHandler
    fun onHit(event: ProjectileHitEvent) {
        val entity = event.entity

        if (entity.location.world == SkyLands.WORLD_SKYPVP && entity.location.blockY >= SkyLands.getSpawnHeight()) {
            entity.remove()
        }
    }

}