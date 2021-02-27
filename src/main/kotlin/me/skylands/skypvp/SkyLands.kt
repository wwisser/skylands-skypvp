package me.skylands.skypvp

import me.skylands.skypvp.command.AbstractCommand
import me.skylands.skypvp.config.DiscoConfig
import me.skylands.skypvp.config.MotdConfig
import me.skylands.skypvp.task.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class SkyLands : JavaPlugin() {

    companion object {
        const val CONFIG_PATH: String = "plugins/SkyLands-SkyPvP"

        val WORLD_SKYPVP: World = Bukkit.getWorld("world")
        val LOCATION_SPAWN: Location = Location(WORLD_SKYPVP, -53.5, 183.0, -351.5, 0f, 0f)
        val SPAWN_HEIGHT: Int = WORLD_SKYPVP.spawnLocation.blockY - 20
        const val VOID_HEIGHT: Int = -15

        lateinit var motdConfig: MotdConfig
        lateinit var discoConfig: DiscoConfig
    }

    override fun onEnable() {
        motdConfig = MotdConfig()
        discoConfig = DiscoConfig()

        PackageClassIndexer.resolveInstances("me.skylands.skypvp.listener", Listener::class.java)
            .forEach { super.getServer().pluginManager.registerEvents(it, this) }

        PackageClassIndexer.resolveInstances("me.skylands.skypvp.command", AbstractCommand::class.java)
            .forEach { super.getCommand(it.getName()).executor = it }

        super.getServer().scheduler.runTaskTimer(this, DiscoUpdateTask(), 15L, 15L) // 1s
        super.getServer().scheduler.runTaskTimer(this, YoloBootsUpdateTask(), 0L, 5L) // 1s
        super.getServer().scheduler.runTaskTimer(this, FlyDisableTask(), 0L, 15L) // 1s
        super.getServer().scheduler.runTaskTimer(this, PlayerVoidKillTask(), 0L, 15L) // 1s
        super.getServer().scheduler.runTaskTimer(this, TablistUpdateTask(), 0L, 20L) // 1s
        super.getServer().scheduler.runTaskTimer(this, MotdUpdateTask(), 0L, 20L * 60 * 3) // 3m
    }

    override fun onDisable() {
        discoConfig.save()
    }

}