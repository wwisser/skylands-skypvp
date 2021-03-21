package me.skylands.skypvp.listener

import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.inventory.ItemStack
import java.util.concurrent.ThreadLocalRandom

class EntityDeathListener : Listener {

    @EventHandler
    fun onEntityDeath(event: EntityDeathEvent) {
        if (event.entity.type != EntityType.PIG_ZOMBIE) {
            return
        }
        val drops = event.drops
        drops.clear()
        if (ThreadLocalRandom.current().nextInt(100) < 20) {
            drops.add(ItemStack(Material.GOLD_NUGGET))
        }
    }

}