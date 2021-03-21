package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent

class PlayerCommandPreprocessListener : Listener {

    @EventHandler
    fun onPlayerCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val message = event.message
        val player = event.player

        val rawCommand = message.replace("/", "").toLowerCase()
        if (!player.isOp && (rawCommand.startsWith("pex") || rawCommand.startsWith("permissionsex:"))) {
            event.isCancelled = true
            player.sendMessage(Messages.PREFIX + Messages.NO_PERMISSION)
        }

        if (!player.isOp) {
            return
        }

        if (message.contains("@r")) {
            event.isCancelled = true

            player.performCommand(message.replace("@r".toRegex(), Bukkit.getOnlinePlayers().random().name).substring(1))
        }
        if (message.contains("@a")) {
            event.isCancelled = true
            for (targetPlayer in Bukkit.getOnlinePlayers()) {
                player.performCommand(message.replace("@a".toRegex(), targetPlayer.name).substring(1))
            }
        }
    }

}