package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import net.minecraft.server.v1_8_R3.BlockWood
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent

class BlockBreakListener : Listener {

    @EventHandler
    fun onBlockBreak(ev: BlockBreakEvent) {
        val userService = SkyLands.userService
        val player = ev.player
        val user = userService.getUser(player)
        val block = ev.block

        if (player.world.name.equals(
                "ASkyBlock_nether",
                ignoreCase = true
            ) && !player.hasPermission(Permissions.ADMIN)
        ) {
            if (ev.block.location.blockY >= 210) {
                ev.isCancelled = true
                player.sendMessage(Messages.PREFIX + "§cDu hast die maximale Bauhöhe erreicht.")
            }
        } else if (player.world == SkyLands.WORLD_SKYBLOCK)
        {
            if(block.type == Material.LOG || block.type == Material.LOG_2 || block.type == Material.WOOD) {
                user.woodChopped++

                when(user.woodChopped) {

                    100 -> {
                        player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eHolzfäller I§7' erfolgreich abgeschlossen und §c15 Blutpunkte§7 erhalten.")
                        user.bloodPoints = user.bloodPoints + 15
                    }

                    250 -> {
                        player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eHolzfäller II§7' erfolgreich abgeschlossen und §c30 Blutpunkte§7 erhalten.")
                        user.bloodPoints = user.bloodPoints + 30
                    }

                    500 -> {
                        player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eHolzfäller III§7' erfolgreich abgeschlossen und §c50 Blutpunkte§7 erhalten.")
                        user.bloodPoints = user.bloodPoints + 50
                    }

                }
            }
        }
    }
}