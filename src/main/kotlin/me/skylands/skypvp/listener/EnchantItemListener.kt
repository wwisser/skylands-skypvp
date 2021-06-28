package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.user.User
import me.skylands.skypvp.user.UserService
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent
import org.bukkit.event.enchantment.PrepareItemEnchantEvent
import org.bukkit.inventory.InventoryView
import kotlin.math.round

class EnchantItemListener : Listener {

    @EventHandler
    fun onPreEnchantItem(event: PrepareItemEnchantEvent) {
        val userService = SkyLands.userService
        val player = event.enchanter
        val user = userService.getUser(player)

        if(user.hasReducedEnchantingCostsUpgrade) {
            event.expLevelCostsOffered[0] = event.expLevelCostsOffered[0] - (event.expLevelCostsOffered[0] * 0.15).toInt()
            event.expLevelCostsOffered[1] = event.expLevelCostsOffered[1] - (event.expLevelCostsOffered[1] * 0.15).toInt()
            event.expLevelCostsOffered[2] = event.expLevelCostsOffered[2] - (event.expLevelCostsOffered[2] * 0.15).toInt()
        }
    }

    @EventHandler(ignoreCancelled = true)
    fun onEnchantItem(event: EnchantItemEvent) {
        val player = event.enchanter
        val item = event.item

        event.enchanter.level = event.enchanter.level - event.expLevelCost + (event.whichButton() + 1)
        event.expLevelCost = 0


        if (!event.isCancelled) {
            if (item.amount > 1) {
                player.closeInventory()
                event.isCancelled = true
                player.sendMessage(Messages.PREFIX + "§cDu darfst keine gestackten Items verzaubern.")
                player.playSound(player.location, Sound.CREEPER_HISS, 1f, 1f)
            }
        }
    }
}
