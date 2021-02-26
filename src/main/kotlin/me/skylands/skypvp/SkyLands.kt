package me.skylands.skypvp

import me.skylands.skypvp.listener.ServerListPingListener
import org.bukkit.plugin.java.JavaPlugin

class SkyLands : JavaPlugin() {

    override fun onEnable() {
        super.getServer().pluginManager.registerEvents(ServerListPingListener(), this)
    }

}