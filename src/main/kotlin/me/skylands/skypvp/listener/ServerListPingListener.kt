package me.skylands.skypvp.listener

import me.skylands.skypvp.config.MotdConfig
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class ServerListPingListener(private val config: MotdConfig) : Listener {

    @EventHandler
    fun onServerPing(event: ServerListPingEvent) {
        event.motd = this.config.getHeader() + "\n" + this.config.getFooter()
    }

}