package me.skylands.skypvp.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class ServerListPingListener : Listener {

    @EventHandler
    fun onServerPing(event: ServerListPingEvent) {
        event.motd = "§6§lSkyLands.me\n§cWartungsarbeiten"
    }

}