package me.skylands.skypvp.task

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.user.User
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class PlaytimeUpdateTask : BukkitRunnable() {

    override fun run() {
        for (onlinePlayer in Bukkit.getOnlinePlayers()) {
            val user: User = SkyLands.userService.getUser(onlinePlayer)
            user.increasePlaytime()
        }
    }

}