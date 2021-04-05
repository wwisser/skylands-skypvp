package me.skylands.skypvp

import me.skylands.skypvp.clan.Clans
import me.skylands.skypvp.clan.util.clan.Clan
import me.skylands.skypvp.command.AbstractCommand
import me.skylands.skypvp.config.DiscoConfig
import me.skylands.skypvp.config.MotdConfig
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.stats.context.impl.internal.KillToplistContext
import me.skylands.skypvp.task.*
import me.skylands.skypvp.user.UserService
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitRunnable
import java.lang.RuntimeException

class SkyLands : JavaPlugin() {

    companion object {
        const val CONFIG_PATH: String = "plugins/SkyLands-SkyPvP"

        lateinit var WORLD_SKYPVP: World
        lateinit var LOCATION_SPAWN: Location
        const val VOID_HEIGHT: Int = -15

        lateinit var motdConfig: MotdConfig
        lateinit var discoConfig: DiscoConfig
        lateinit var peaceConfig: PeaceConfig

        lateinit var userService: UserService
        lateinit var plugin: JavaPlugin
        var vaultChat: Chat? = null

        fun getChat(): Chat {
            return vaultChat!!
        }

        fun getSpawnHeight(): Int {
            return WORLD_SKYPVP.spawnLocation.blockY - 20
        }
    }

    override fun onEnable() {
        try {
            plugin = this
            motdConfig = MotdConfig()
            discoConfig = DiscoConfig()
            peaceConfig = PeaceConfig()
            userService = UserService()

            vaultChat = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java).provider
            WORLD_SKYPVP = Bukkit.getWorld("world")
            LOCATION_SPAWN = Location(WORLD_SKYPVP, 57.5, 123.0, 137.5, 0f, 0f)

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
            super.getServer().scheduler.runTaskTimerAsynchronously(
                this,
                ToplistUpdateTask(arrayOf(KillToplistContext(userService))),
                0L,
                20L * 60 * 5
            ) // 5m

            Bukkit.getOnlinePlayers().forEach { userService.loadUser(it) }

            super.getServer().scheduler.runTaskTimer(
                this,
                { ScoreboardUpdateTask.setShowTopList(!ScoreboardUpdateTask.isShowTopList()) },
                20L * 60,
                20L * 60 * 2
            ) // 2m
            super.getServer().scheduler.runTaskTimer(
                this,
                ScoreboardUpdateTask(),
                0L,
                10L
            )

            super.getServer().scheduler.runTaskTimer(
                this,
                CombatUpdateTask(),
                5L,
                5L
            )

            Clans().onEnable(this)
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