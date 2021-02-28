package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.nms.ActionBar
import org.bukkit.entity.Player
import org.bukkit.entity.Projectile
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent

class EntityDamageByEntityListener : Listener {

    private val peaceConfig: PeaceConfig = SkyLands.peaceConfig

    @EventHandler
    fun onEntityDamageByEntity(event: EntityDamageByEntityEvent) {
        if (event.entity.world == SkyLands.WORLD_SKYPVP) {
            if (event.entity !is Player) {
                return
            }
            val damager = event.damager
            val victim = event.entity as Player
            if (damager is Player &&
                peaceConfig.hasPeace(victim.uniqueId.toString(), damager.getUniqueId().toString())
            ) {
                event.isCancelled = true
                ActionBar.send("§cDu hast mit " + victim.name + " Frieden geschlossen.", damager)
            }
            if (damager !is Projectile) {
                return
            }
            if (damager.shooter is Player) {
                val shooter = damager.shooter as Player
                if (shooter !== victim &&
                    peaceConfig.hasPeace(victim.uniqueId.toString(), shooter.uniqueId.toString())
                ) {
                    event.isCancelled = true
                    ActionBar.send("§cDu hast mit " + victim.name + " Frieden geschlossen.", shooter)
                }
            }
        }
    }
}
