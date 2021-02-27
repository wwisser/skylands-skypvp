package me.skylands.skypvp.task

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.nms.Tablist
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class TablistUpdateTask : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers().forEach {
           updateTablist(it)
        }
    }

    companion object {
        fun updateTablist(it: Player) {
            var footer = "§7Du befindest dich gerade "
            val world: World = it.world


            footer += if (world == SkyLands.WORLD_SKYPVP) {
                if (it.location.blockY < SkyLands.SPAWN_HEIGHT) {
                    "im §eSkyPvP §7Bereich."
                } else {
                    "am §eSpawn§7."
                }
            } else {
                "in der §eSkyBlock §7Welt."
            }

            Tablist.send(
                it,
                listOf(
                    "",
                    "      §6§lSkyLands §7- Einzigartiges SkyPvP",
                    ""
                ).joinToString(System.lineSeparator()),
                listOf(
                    "",
                    footer,
                    ""
                ).joinToString(System.lineSeparator())
            )
        }
    }

}