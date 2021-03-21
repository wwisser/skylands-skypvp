package me.skylands.skypvp

import me.skylands.skypvp.command.AbstractCommand
import me.skylands.skypvp.config.DiscoConfig
import me.skylands.skypvp.config.MotdConfig
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.task.*
import me.skylands.skypvp.user.UserService
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.lang.RuntimeException

class SkyLands : JavaPlugin() {

    companion object {
        const val CONFIG_PATH: String = "plugins/SkyLands-SkyPvP"

        val WORLD_SKYPVP: World = Bukkit.getWorld("world")
        val LOCATION_SPAWN: Location = Location(WORLD_SKYPVP, 57.5, 123.0, 137.5, 0f, 0f)
        val SPAWN_HEIGHT: Int = WORLD_SKYPVP.spawnLocation.blockY - 20
        const val VOID_HEIGHT: Int = -15

        lateinit var motdConfig: MotdConfig
        lateinit var discoConfig: DiscoConfig
        lateinit var peaceConfig: PeaceConfig

        lateinit var userService: UserService
        lateinit var vaultChat: Chat
    }

    override fun onEnable() {
        try {
            println("enabled")
            motdConfig = MotdConfig()
            discoConfig = DiscoConfig()
            peaceConfig = PeaceConfig()
            userService = UserService()

            //vaultChat = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java).provider

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
            super.getServer().scheduler.runTaskTimer(this, MotdUpdateTask(), 0L, 20L * 60 * 3) // 3m
            super.getServer().scheduler.runTaskTimer(this, PlaytimeUpdateTask(), 20L * 60, 20L * 60) // 1m

            Bukkit.getOnlinePlayers().forEach { userService.loadUser(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    override fun onDisable() {
        try {
            Bukkit.getOnlinePlayers().forEach { userService.unloadUser(it) }
            discoConfig.save()
            peaceConfig.save()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

}