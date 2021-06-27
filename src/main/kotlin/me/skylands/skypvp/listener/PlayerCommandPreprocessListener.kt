package me.skylands.skypvp.listener

import com.wasteofplastic.askyblock.ASkyBlockAPI
import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.combat.CombatService
import me.skylands.skypvp.command.CommandCmdspy
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate
import me.skylands.skypvp.container.template.impl.bloodpoints.ChallengesShopContainerTemplate
import me.skylands.skypvp.container.template.impl.bloodpoints.EffectsMenuShopContainerTemplate
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerCommandPreprocessEvent
import org.bukkit.potion.PotionEffectType
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
        "permissionsex:demote",
        "permissionsex:pex"
    )

    private val challengesShopContainerTemplate = ChallengesShopContainerTemplate(SkyLands.containerManager, ShopContainerTemplate(SkyLands.containerManager))

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

        if(rawCommand == "spawn") {
            if(player.hasPotionEffect(PotionEffectType.SPEED)) player.removePotionEffect(PotionEffectType.SPEED)
            if(player.hasPotionEffect(PotionEffectType.FAST_DIGGING)) player.removePotionEffect(PotionEffectType.FAST_DIGGING)
            if(player.hasPotionEffect(PotionEffectType.WATER_BREATHING)) player.removePotionEffect(PotionEffectType.WATER_BREATHING)
        }

        if(rawCommand == "challenge" || rawCommand == "challenges" || rawCommand == "askyblock:challenge" || rawCommand == "askyblock:challenges" || rawCommand == "c") {
            challengesShopContainerTemplate.openContainer(player)
            event.isCancelled = true
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
