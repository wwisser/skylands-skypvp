package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerKickEvent

class PlayerKickListener: Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onPlayerKick(event: PlayerKickEvent) {
        if (event.reason.contains("disconnect.spam")) {
            event.isCancelled = false
        }
    }

}