package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryType

class InventoryCloseListener : Listener {

    @EventHandler
    fun onInventoryClose(event: InventoryCloseEvent) {
        if (event.inventory.type == InventoryType.ENCHANTING) {
            event.inventory.setItem(1, null)
        }
    }

}