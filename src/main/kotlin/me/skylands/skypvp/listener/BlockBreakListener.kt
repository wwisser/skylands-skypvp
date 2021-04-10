package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {

    @EventHandler
    fun onBlockBreak(ev: BlockBreakEvent) {
        val player = ev.player
        if (player.world.name.equals(
                "ASkyBlock_nether",
                ignoreCase = true
            ) && !player.hasPermission(Permissions.ADMIN)
        ) {
            if (ev.block.location.blockY >= 210) {
                ev.isCancelled = true
                player.sendMessage(Messages.PREFIX + "§cDu hast die maximale Bauhöhe erreicht.")
            }
        }
    }

}