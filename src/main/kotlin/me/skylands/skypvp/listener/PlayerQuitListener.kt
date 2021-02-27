package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        event.quitMessage = "§7[§8§l-§7] ${event.player.name}"
    }

}