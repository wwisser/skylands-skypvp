package me.skylands.skypvp.task

import me.skylands.skypvp.nms.Tablist
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

class TablistUpdateTask : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers().forEach {
            Tablist.send(
                it,
                listOf(
                    "",
                    "§6§lSkyLands",
                    "§7Einzigartiges Level-System",
                    ""
                ).joinToString(System.lineSeparator()),
                listOf(
                    "",
                    "§7Vote und erhalte tolle Belohnungen: §6/vote",
                    ""
                ).joinToString(System.lineSeparator())
            )
        }
    }

}