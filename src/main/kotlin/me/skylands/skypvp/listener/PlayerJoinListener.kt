package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.task.TablistUpdateTask
import me.skylands.skypvp.user.FileUserRepository
import me.skylands.skypvp.user.UserRepository
import me.skylands.skypvp.util.FontUtils
import me.skylands.skypvp.util.Title
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoinListener : Listener {

    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        if(player.world.equals(Bukkit.getWorld("world"))) player.teleport(SkyLands.LOCATION_SPAWN)

        Title("§6§lSkyLands", "§7SkyPvP & SkyBlock", 0, 2, 1).send(player)

        if (!player.hasPlayedBefore()) {
            Bukkit.getOnlinePlayers()
                .stream()
                .filter { onlinePlayer: Player -> onlinePlayer !== player }
                .forEach { onlinePlayer: Player ->
                    onlinePlayer.sendMessage(
                        Messages.PREFIX + "§7Willkommen auf §6§lSkyLands§7," +
                                " §e" + player.name + "§7! "
                                + "§7[§e#§l" + Bukkit.getOfflinePlayers().size + "§7]"
                    )
                }

            player.teleport(SkyLands.LOCATION_SPAWN)
            event.joinMessage = null
            PlayerRespawnListener.equipWithKit(player)
        } else {
            event.joinMessage = "§7[§6§l+§7] ${player.name}"
        }

        FontUtils.sendCenteredMessage(player, Messages.PREFIX_LONG_CENTER)
        player.sendMessage("")
        FontUtils.sendCenteredMessage(player, "§7Herzlich Willkommen auf §6§lSkyLands§7!")
        FontUtils.sendCenteredMessage(player, "§7SkyPvP im Einklang mit Handel & Wirtschaft.")
        player.sendMessage("")
        FontUtils.sendCenteredMessage(player, "§7Wichtige Befehle:")
        FontUtils.sendCenteredMessage(player, "§e/level§8, §e/is§8, §e/kit§8, §e/vote§8, §e/clan")
        player.sendMessage("")
        FontUtils.sendCenteredMessage(player, Messages.PREFIX_LONG_CENTER)


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