package me.skylands.skypvp.task

import me.skylands.skypvp.SkyLands
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable

class DiscoUpdateTask : BukkitRunnable() {

    override fun run() {
        for (location in SkyLands.discoConfig.locations.values) {
            val wool = location.block
            if (wool.type == Material.WOOL) {
                if (wool.data.toInt() == 13) {
                    wool.data = 0.toByte()
                } else {
                    wool.data = 13.toByte()
                }
            }
        }
    }

}