package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.BrewEvent

class BrewListener : Listener {

    @EventHandler
    fun onBrewEvent(event: BrewEvent) {
        if (event.contents != null && event.contents.contents != null) {
            for (item in event.contents.contents) {
                if (item.amount > item.maxStackSize) {
                    item.amount = item.maxStackSize
                }
            }
        }
    }

}