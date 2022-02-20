package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.nms.ActionBar.send
import me.skylands.skypvp.pve.Helper
import org.bukkit.Material
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
        val helper = Helper()

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
                        if (helper.hasConverterPotion(player.name)) {
                            player.level = player.level + 15
                            send("§aUmgewandelt§7! Du hast §a15 Level§7 erhalten", player)
                            return
                        }
                        user.bloodPoints = user.bloodPoints + 15
                    }

                    250 -> {
                        player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eHolzfäller II§7' erfolgreich abgeschlossen und §c30 Blutpunkte§7 erhalten.")
                        if (helper.hasConverterPotion(player.name)) {
                            player.level = player.level + 30
                            send("§aUmgewandelt§7! Du hast §a30 Level§7 erhalten", player)
                            return
                        }
                        user.bloodPoints = user.bloodPoints + 30
                    }

                    500 -> {
                        player.sendMessage(Messages.PREFIX + "§aGlückwunsch§7! Du hast die Challenge '§eHolzfäller III§7' erfolgreich abgeschlossen und §c50 Blutpunkte§7 erhalten.")
                        if (helper.hasConverterPotion(player.name)) {
                            player.level = player.level + 50
                            send("§aUmgewandelt§7! Du hast §50 Level§7 erhalten", player)
                            return
                        }
                        user.bloodPoints = user.bloodPoints + 50
                    }

                }
            }
        }
    }
}