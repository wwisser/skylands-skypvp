package me.skylands.skypvp.task

import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class PlayerVoidKillTask : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers()
            .stream()
            .filter {
                !it.hasPermission(Permissions.TEAM)
                        && it.location.blockY <= SkyLands.VOID_HEIGHT
                        && it.world == SkyLands.WORLD_SKYPVP
            }
            .forEach { player: Player -> player.health = 0.0 }
    }

}