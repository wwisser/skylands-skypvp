package me.skylands.skypvp.listener

import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.event.inventory.InventoryType
import org.bukkit.inventory.EnchantingInventory
import org.bukkit.inventory.ItemStack

class InventoryOpenListener : Listener {

    @EventHandler
    fun onInventoryOpen(event: InventoryOpenEvent) {
        if (event.inventory.type == InventoryType.ENCHANTING) {
            (event.inventory as EnchantingInventory).secondary =
                ItemStack(Material.INK_SACK, 64, 4.toShort())
        }
    }

}