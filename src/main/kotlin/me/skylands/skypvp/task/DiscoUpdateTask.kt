package me.skylands.skypvp.task

import me.skylands.skypvp.SkyLands
import org.bukkit.Material
import org.bukkit.scheduler.BukkitRunnable

class DiscoUpdateTask : BukkitRunnable() {

    override fun run() {
        for (location in SkyLands.discoConfig.locations.values) {
            val wool = location.block
            if (wool.type == Material.WOOL) {
                if (wool.data.toInt() == 4) {
                    wool.data = 11.toByte()
                } else {
                    wool.data = 4.toByte()
                }
            }
        }
    }

}