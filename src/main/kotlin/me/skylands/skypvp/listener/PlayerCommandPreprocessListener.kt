package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.combat.CombatService
import me.skylands.skypvp.command.CommandCmdspy
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import java.util.*

class PlayerCommandPreprocessListener : Listener {

    private val blockedCommands: List<String> = listOf(
        "pex",
        "permissionsex",
        "calc",
        "calculate",
        "eval",
        "evaluate",
        "solve",
        "execute",
        "demote",
        "promote",
        "hd",
        "holographicdisplays",
        "holo",
        "hologram",
        "worldedit",
        "worldedit:calc",
        "worldedit:calculate",
        "worldedit:eval",
        "worldedit:evaluate",
        "worldedit:solve",
        "worldedit:execute",
        "permissionsex:promote",
        "permissionsex:demote"
    )

    @EventHandler
    fun onPlayerCommandPreprocess(event: PlayerCommandPreprocessEvent) {
        val message = event.message
        val player = event.player

        this.emitMessage(message, player)

        val rawCommand = message.replace("/", "").split(" ")[0].toLowerCase()
        if (!player.isOp && blockedCommands.any { it.startsWith(rawCommand) }) {
            event.isCancelled = true
            player.sendMessage(Messages.PREFIX + Messages.NO_PERMISSION)
        }

        val command = event.message

        if (!player.isOp && CombatService.isFighting(player) && !CombatService.isCommandAllowed(command)) {
            event.isCancelled = true
            player.sendMessage(Messages.PREFIX + "§cDu darfst diesen Befehl im Kampf nicht verwenden.")
        }

        if (!player.isOp && CombatService.isCommandTeleportable(command)
            && player.world == SkyLands.WORLD_SKYPVP && player.location.blockY < SkyLands.getSpawnHeight()
        ) {
            event.isCancelled = true
            player.sendMessage(Messages.PREFIX + "§cDu darfst diesen Befehl hier nicht verwenden.")
        }

        if (!player.isOp) {
            return
        }

        if (message.contains("@r")) {
            event.isCancelled = true

            player.performCommand(message.replace("@r".toRegex(), Bukkit.getOnlinePlayers().random().name).substring(1))
        }
        if (message.contains("@a")) {
            event.isCancelled = true
            for (targetPlayer in Bukkit.getOnlinePlayers()) {
                player.performCommand(message.replace("@a".toRegex(), targetPlayer.name).substring(1))
            }
        }
    }

    private fun emitMessage(
        message: String,
        emitter: Player,
    ) {
        val entries: List<UUID> = CommandCmdspy.commandSpy
        for (uuid: UUID in entries) {
            val player = Bukkit.getPlayer(uuid)
            if (player != null && player !== emitter) {
                player.sendMessage(Messages.PREFIX + "§c" + emitter.name + " §f:: §7" + message)
            }
        }
    }

}