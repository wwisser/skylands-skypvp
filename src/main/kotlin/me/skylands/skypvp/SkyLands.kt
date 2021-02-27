package me.skylands.skypvp

import me.skylands.skypvp.command.AbstractCommand
import me.skylands.skypvp.config.MotdConfig
import me.skylands.skypvp.task.TablistUpdateTask
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class SkyLands : JavaPlugin() {

    companion object {
        const val CONFIG_PATH: String = "plugins/SkyLands-SkyPvP"

        lateinit var motdConfig: MotdConfig
    }

    override fun onEnable() {
        PackageClassIndexer.resolveInstances("me.skylands.skypvp.listener", Listener::class.java)
            .forEach { super.getServer().pluginManager.registerEvents(it, this) }

        PackageClassIndexer.resolveInstances("me.skylands.skypvp.command", AbstractCommand::class.java)
            .forEach { super.getCommand(it.getName()).executor = it }

        super.getServer().scheduler.runTaskTimer(this, TablistUpdateTask(), 0L, 20L) // 1s
        super.getServer().scheduler.runTaskTimer(this, TablistUpdateTask(), 0L, 20L * 60 * 3) // 3m
        motdConfig = MotdConfig()
    }

}