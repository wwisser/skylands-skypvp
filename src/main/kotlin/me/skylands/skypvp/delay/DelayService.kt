package me.skylands.skypvp.delay

import me.skylands.skypvp.Messages
import me.skylands.skypvp.util.Formatter
import org.bukkit.entity.Player
import java.util.*
import java.util.function.Consumer
import kotlin.collections.HashMap

object DelayService {

    private val configurations: MutableMap<DelayConfig, UUID> = HashMap()
    private val delays: MutableMap<UUID, MutableMap<UUID, Long>> = HashMap()

    fun handleDelay(player: Player, configuration: DelayConfig, action: Consumer<Player>): Boolean {
        return handle(player, configuration, action, false)
    }

    fun handleDelayInverted(player: Player, configuration: DelayConfig, action: Consumer<Player>): Boolean {
        return handle(player, configuration, action, true)
    }

    /**
     * @return whether delay is active
     */
    private fun handle(
        player: Player,
        configuration: DelayConfig,
        action: Consumer<Player>,
        inverted: Boolean
    ): Boolean {
        val uuid = player.uniqueId
        if (!configurations.containsKey(configuration)) {
            configurations[configuration] = UUID.randomUUID()
            if (!inverted) {
                action.accept(player)
            }
            addDelay(uuid, configuration)
            return false
        }
        if (!delays.containsKey(uuid)) {
            if (!inverted) {
                action.accept(player)
            }
            addDelay(uuid, configuration)
            return false
        }
        val configUuid = configurations[configuration]!!
        val delayEntry: MutableMap<UUID, Long> = delays.getOrDefault(uuid, HashMap())
        return if (delayEntry.containsKey(configUuid)) {
            val endTimeStamp = delayEntry.getOrDefault(configUuid, System.currentTimeMillis())
            if (System.currentTimeMillis() >= endTimeStamp) {
                if (!inverted) {
                    action.accept(player)
                }
                addDelay(uuid, configuration)
                false
            } else {
                if (!inverted) {
                    if (configuration.message != null) {
                        val pendingTime = endTimeStamp - System.currentTimeMillis()
                        val formattedTime: String = Formatter.formatMillis(pendingTime)
                        player.sendMessage(
                            Messages.PREFIX + "§c" + configuration.message
                                .replace("%time", formattedTime)
                        )
                    }
                } else {
                    action.accept(player)
                }
                true
            }
        } else {
            delayEntry[configUuid] = System.currentTimeMillis() + configuration.time
            delays[uuid] = delayEntry
            if (!inverted) {
                action.accept(player)
            }
            false
        }
    }

    private fun addDelay(uuid: UUID, delayConfiguration: DelayConfig) {
        val delayEntry: MutableMap<UUID, Long> = delays.getOrDefault(uuid, HashMap())
        delayEntry[configurations[delayConfiguration]!!] = System.currentTimeMillis() + delayConfiguration.time
        delays[uuid] = delayEntry
    }

}