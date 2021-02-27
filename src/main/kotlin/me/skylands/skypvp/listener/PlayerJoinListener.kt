package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.task.TablistUpdateTask
import me.skylands.skypvp.user.FileUserRepository
import me.skylands.skypvp.user.UserRepository
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player

        event.joinMessage = "§7[§6§l+§7] ${player.name}"
        player.playSound(player.location, Sound.LEVEL_UP, Float.MAX_VALUE, Float.MIN_VALUE)
        TablistUpdateTask.updateTablist(player)

        val userRepository: UserRepository = SkyLands.userService.userRepository

        if (userRepository is FileUserRepository) {
            userRepository.updateUuid(player)
        }

        val loadUser = SkyLands.userService.loadUser(player)
        player.level = loadUser.level
        SkyLands.userService.getUser(player).lastSeen = System.currentTimeMillis()
    }

}