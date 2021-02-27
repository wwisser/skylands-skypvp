package me.skylands.skypvp.listener

import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "§7[§6§l+§7] ${player.name}"
        player.playSound(player.location, Sound.LEVEL_UP, Float.MAX_VALUE, Float.MIN_VALUE)
    }

}