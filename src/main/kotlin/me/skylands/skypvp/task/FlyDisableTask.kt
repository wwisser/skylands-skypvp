package me.skylands.skypvp.task

import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.scheduler.BukkitRunnable

class FlyDisableTask : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers()
            .stream()
            .filter {
                it.world == SkyLands.WORLD_SKYPVP && it.location.blockY < SkyLands.SPAWN_HEIGHT
                        && !it.hasPermission(Permissions.TEAM)
            }
            .forEach { player: Player ->
                player.isFlying = false
                player.allowFlight = false
            }
    }

}
