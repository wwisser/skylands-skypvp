package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.combat.CombatService
import me.skylands.skypvp.stats.LastHitCache
import me.skylands.skypvp.user.User
import me.skylands.skypvp.user.UserService
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import java.util.*
import kotlin.math.roundToInt

class PlayerDeathListener : Listener {

    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {
        event.keepLevel = true
        event.droppedExp = 0
    }

    @EventHandler
    fun onPlayerDeathStats(event: PlayerDeathEvent) {
        val userService: UserService = SkyLands.userService
        val victim = event.entity
        var killer = victim.killer
        event.deathMessage = null
        victim.playSound(victim.location, Sound.BLAZE_DEATH, 1f, 1f)
        val user: User = userService.getUser(victim)
        user.deaths = user.deaths + 1
        CombatService.detachFight(victim, false)

        object : BukkitRunnable() {
            override fun run() {
                victim.spigot().respawn()
            }
        }.runTaskLater(SkyLands.plugin, 3L)

        if (killer == null && LastHitCache.lastHits.containsKey(victim)) {
            killer = LastHitCache.lastHits[victim]
            LastHitCache.lastHits.remove(victim)
        }
        if (killer != null && killer !== victim) {
            val killerUser: User = userService.getUser(killer)
            killerUser.kills = killerUser.kills + 1
            killer.sendMessage(Messages.PREFIX + "Du hast §e" + victim.name + " §7getötet! +§a3 Level")
            killer.playSound(killer.location, Sound.SUCCESSFUL_HIT, 100f, 100f)
            killer.addPotionEffect(PotionEffect(PotionEffectType.REGENERATION, 10, 5))
            killer.level = killer.level + 3
            victim.sendMessage(
                Messages.PREFIX + "Du wurdest von §e"
                        + killer.name
                        + " §7mit §c"
                        + formatHealth(killer.health)
                        + " ❤ §7getötet."
            )
        }
        LastHitCache.lastHits.remove(victim)


        val drops: MutableList<ItemStack> = ArrayList(event.drops)

        event.drops
            .stream()
            .filter { itemStack: ItemStack ->
                (itemStack.hasItemMeta()
                        && itemStack.itemMeta.hasDisplayName()
                        && !itemStack.itemMeta.hasEnchants()
                        && itemStack.itemMeta.displayName == PlayerRespawnListener.AUTO_KIT_NAME)
            }
            .forEach { o: ItemStack -> drops.remove(o) }

        event.drops.clear()
        event.drops.addAll(drops)
    }

    private fun formatHealth(health: Double): String {
        return (((health / 2) * 100).roundToInt() / 100).toString()
    }

}