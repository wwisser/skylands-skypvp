package me.skylands.skypvp.task

import me.skylands.skypvp.config.MotdConfig
import org.bukkit.scheduler.BukkitRunnable

class MotdUpdateTask(private val config: MotdConfig) : BukkitRunnable() {

    override fun run() {
        this.config.reloadConfig()
    }

}