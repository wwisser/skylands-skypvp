package me.skylands.skypvp

import com.github.fierioziy.particlenativeapi.api.ParticleNativeAPI
import com.github.fierioziy.particlenativeapi.plugin.ParticleNativePlugin
import com.wasteofplastic.askyblock.ASkyBlockAPI
import me.skylands.skypvp.clan.Clans
import me.skylands.skypvp.command.AbstractCommand
import me.skylands.skypvp.config.DiscoConfig
import me.skylands.skypvp.config.MotdConfig
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.config.TotemConfig
import me.skylands.skypvp.container.ContainerManager
import me.skylands.skypvp.ipmatching.IpMatchingService
import me.skylands.skypvp.pve.BossTracker
import me.skylands.skypvp.pve.Helper
import me.skylands.skypvp.pve.bosses.BossSlime
import me.skylands.skypvp.stats.context.impl.external.IslandLevelToplistContext
import me.skylands.skypvp.stats.context.impl.internal.*
import me.skylands.skypvp.task.*
import me.skylands.skypvp.task.pve.PreventBossIslandEnterTask
import me.skylands.skypvp.task.pve.TotemEnemiesSpawnTask
import me.skylands.skypvp.user.UserService
import me.skylands.skypvp.util.LevelEconomy
import me.skylands.skypvp.util.RandomMessages
import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import net.minecraft.server.v1_8_R3.EntitySlime
import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import org.bukkit.plugin.ServicePriority
import org.bukkit.plugin.java.JavaPlugin
import java.util.*

class SkyLands : JavaPlugin() {

    companion object {
        const val CONFIG_PATH: String = "plugins/SkyLands-SkyPvP"

        lateinit var WORLD_SKYPVP: World
        lateinit var WORLD_SKYBLOCK: World
        lateinit var LOCATION_SPAWN: Location
        const val VOID_HEIGHT: Int = -15

        lateinit var motdConfig: MotdConfig
        lateinit var discoConfig: DiscoConfig
        lateinit var peaceConfig: PeaceConfig
        lateinit var totemConfig: TotemConfig
        lateinit var userService: UserService
        lateinit var randomMessages: RandomMessages

        lateinit var containerManager: ContainerManager
        lateinit var ipMatchingService: IpMatchingService
        lateinit var plugin: JavaPlugin
        lateinit var particleAPI: ParticleNativeAPI
        var vaultChat: Chat? = null

        fun getChat(): Chat {
            return vaultChat!!
        }

        fun getSpawnHeight(): Int {
            return WORLD_SKYPVP.spawnLocation.blockY - 20
        }
    }

    override fun onLoad() {
        Bukkit.getServicesManager().register(Economy::class.java, LevelEconomy(), this, ServicePriority.Normal)
    }

    override fun onEnable() {
        try {

            plugin = this
            motdConfig = MotdConfig()
            discoConfig = DiscoConfig()
            peaceConfig = PeaceConfig()
            totemConfig = TotemConfig()
            userService = UserService()
            randomMessages = RandomMessages();

            containerManager = ContainerManager()
            ipMatchingService = IpMatchingService()

            vaultChat = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java).provider
            particleAPI = ParticleNativePlugin.getAPI()
            WORLD_SKYPVP = Bukkit.getWorld("sl_pve")
            WORLD_SKYBLOCK = Bukkit.getWorld("ASkyBlock")
            LOCATION_SPAWN = Location(WORLD_SKYPVP, 57.5, 123.0, 137.5, 0f, 0f)

            PackageClassIndexer.resolveInstances("me.skylands.skypvp.listener", Listener::class.java)
                .forEach { super.getServer().pluginManager.registerEvents(it, this) }

            PackageClassIndexer.resolveInstances("me.skylands.skypvp.command", AbstractCommand::class.java)
                .forEach { super.getCommand(it.getName()).executor = it }

            super.getServer().scheduler.runTaskTimer(this, PreventBossIslandEnterTask(), 20L, 15L * 2)
            super.getServer().scheduler.runTaskTimer(this, DiscoUpdateTask(), 15L, 15L) // 1s
            super.getServer().scheduler.runTaskTimer(this, YoloBootsUpdateTask(), 0L, 5L) // 1s
            super.getServer().scheduler.runTaskTimer(this, FlyDisableTask(), 0L, 15L) // 1s
            super.getServer().scheduler.runTaskTimer(this, PlayerVoidKillTask(), 0L, 15L) // 1s
            super.getServer().scheduler.runTaskTimer(this, TablistUpdateTask(), 0L, 20L) // 1s
            super.getServer().scheduler.runTaskTimer(this, MotdUpdateTask(), 0L, 20L * 60 * 3) // 3m
            super.getServer().scheduler.runTaskTimer(this, MotdUpdateTask(), 0L, 20L * 60 * 3) // 3m
            super.getServer().scheduler.runTaskTimer(this, PlaytimeUpdateTask(), 20L * 60, 20L * 60) // 1m
            super.getServer().scheduler.runTaskTimer(this, JumpboostSupplierTask(), 0L, 3L)
            super.getServer().scheduler.runTaskTimer(this, RandomMessagesTask(), 0L, 20L * 60);
            super.getServer().scheduler.runTaskTimerAsynchronously(
                this,
                ToplistUpdateTask(arrayOf(
                    IslandLevelToplistContext(userService.userRepository, ASkyBlockAPI.getInstance()),
                    KillToplistContext(userService),
                    DeathToplistContext(userService.userRepository),
                    JewelToplistContext(userService.userRepository),
                    PlaytimeToplistContext(userService.userRepository),
                    VoteToplistContext(userService.userRepository))),
                20L,
                20L * 60 * 5
            ) // 5m

            super.getServer().scheduler.runTaskTimer(
                this,
                { Bukkit.getOnlinePlayers().forEach { userService.saveUser(userService.getUser(it)) } },
                20L * 30,
                20L * 30
            )

            super.getServer().scheduler.runTaskTimer(
                this,
                { ScoreboardUpdateTask.setShowTopList(!ScoreboardUpdateTask.isShowTopList()) },
                20L * 61,
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

            super.getServer().scheduler.runTaskTimer(
                this,
                TotemEnemiesSpawnTask(),
                20L,
                20L
            )

            super.getServer().scheduler.runTaskTimer(
                this,
                {
                    Bukkit.getOnlinePlayers().forEach { player: Player ->
                        if (player.world == WORLD_SKYPVP && player.location.blockY > getSpawnHeight()) {
                            for (i in 0..2) {
                                val loc = player.location.add(
                                    this.rdmDouble(10 * -1, 10),
                                    this.rdmDouble(10 * -1, 10),
                                    this.rdmDouble(10 * -1, 10)
                                )
                                val packet = PacketPlayOutWorldParticles(
                                    EnumParticle.VILLAGER_HAPPY, true,
                                    loc.x.toFloat(),
                                    loc.y.toFloat(), loc.z.toFloat(), 0f, 0f, 0f, 0f, 1
                                )
                                (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
                            }
                        }
                    }
                },
                3L,
                3L
            )

            Bukkit.getOnlinePlayers().forEach { userService.loadUser(it) }

            val worldBorder = WORLD_SKYPVP.worldBorder
            worldBorder.center = LOCATION_SPAWN
            worldBorder.size = 500.0

            Clans().onEnable(this)
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }

        val helper = Helper();
        helper.registerEntity("Slime", 55, EntitySlime::class.java, BossSlime::class.java)
    }

    override fun onDisable() {
        val bossTracker = BossTracker();

        Helper.bossData.keys.forEach {
            bossTracker.killBoss(it, true)
        }

        try {
            Bukkit.getOnlinePlayers().forEach { userService.unloadUser(it) }
            discoConfig.save()
            peaceConfig.save()
        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }

    private fun rdmDouble(min: Int, max: Int): Double {
        val random = Random()
        return min + (max - min) * random.nextDouble()
    }
}
