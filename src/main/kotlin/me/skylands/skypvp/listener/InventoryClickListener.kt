package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.AnvilInventory

class InventoryClickListener : Listener {

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val inventory = event.inventory
        if (event.inventory.type == InventoryType.ENCHANTING && event.rawSlot == 1) {
            event.isCancelled = true
        }
        if (inventory !is AnvilInventory) {
            return
        }
        val inventoryView = event.view
        val rawSlot = event.rawSlot
        if (rawSlot == inventoryView.convertSlot(rawSlot) && rawSlot == 2) {
            val itemStack = event.currentItem
            if (itemStack != null && itemStack.type != Material.AIR && itemStack.amount > itemStack.maxStackSize) {
                event.isCancelled = true
                player.closeInventory()
                player.sendMessage(Messages.PREFIX + "§cDu darfst keine gestackten Items im Amboss verwenden.")
                player.playSound(player.location, Sound.CREEPER_HISS, 1f, 1f)
            }
        }

        if (inventory.title.equals(PlayerInteractListener.CRATE_TITLE)) {
            event.isCancelled = true
        }
    }

}