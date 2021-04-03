package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.config.MotdConfig
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.server.ServerListPingEvent

class ServerListPingListener : Listener {

    private val config: MotdConfig = SkyLands.motdConfig

    @EventHandler
    fun onServerPing(event: ServerListPingEvent) {
        event.motd = this.config.getHeader() + "\n" + this.config.getFooter()
    }

}