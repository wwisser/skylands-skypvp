package me.skylands.skypvp.listener

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.delay.DelayConfig
import me.skylands.skypvp.delay.DelayService
import me.skylands.skypvp.nms.ActionBar
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Sound
import org.bukkit.block.Sign
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import java.util.concurrent.TimeUnit

class PlayerInteractListener : Listener {

    companion object {
        val CONFIG: DelayConfig = DelayConfig("§cBitte warte noch 1 Sekunde(n)", TimeUnit.SECONDS.toMillis(1))
        const val PREFIX: String = "§1[Kostenlos]"
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        val player = event.player
        if (player.world != SkyLands.WORLD_SKYPVP) {
            return
        }
        if (event.action == Action.RIGHT_CLICK_BLOCK
            && event.clickedBlock.state is Sign
        ) {
            val sign = event.clickedBlock.state as Sign
            if (!sign.getLine(0).equals(PREFIX, ignoreCase = true)) {
                return
            }
            val delayed: Boolean = DelayService.handleDelayInverted(player, CONFIG) { delayedPlayer ->
                event.isCancelled = true
                player.playSound(player.location, Sound.CREEPER_HISS, 1f, 1f)
                ActionBar.send(CONFIG.message!!, delayedPlayer)
            }
            if (delayed) {
                return
            }
            val idRaw: List<String> = ChatColor.stripColor(sign.getLine(1)).split(":")
            val id = idRaw[0].toInt()
            val sh = idRaw[1].toInt()
            var amount: Int = ChatColor.stripColor(sign.getLine(2)).toInt()
            if (amount > 64) {
                amount = 64
            }
            val item = ItemStack(id, amount, sh.toShort())
            val inventory = Bukkit.createInventory(
                null,
                3 * 9,
                sign.getLine(0) + " " + sign.getLine(3)
            )
            inventory.setItem(13, item)
            player.openInventory(inventory)
            player.playSound(player.location, Sound.SUCCESSFUL_HIT, 1f, 1f)
        }
    }

}