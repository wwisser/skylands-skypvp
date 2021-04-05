package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.combat.CombatService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerQuitListener : Listener {

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val player = event.player
        CombatService.detachFight(player, true)

        event.quitMessage = "§7[§8§l-§7] ${player.name}"

        val user = SkyLands.userService.getUser(player)
        user.level = player.level
        SkyLands.userService.unloadUser(player)
    }

}