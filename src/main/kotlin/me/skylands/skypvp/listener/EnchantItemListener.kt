package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.enchantment.EnchantItemEvent

class EnchantItemListener : Listener {

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