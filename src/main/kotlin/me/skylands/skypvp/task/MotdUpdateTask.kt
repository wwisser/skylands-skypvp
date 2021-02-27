package me.skylands.skypvp.task

import me.skylands.skypvp.SkyLands
import org.bukkit.scheduler.BukkitRunnable

class MotdUpdateTask() : BukkitRunnable() {

    override fun run() {
        SkyLands.motdConfig.reloadConfig()
    }

}