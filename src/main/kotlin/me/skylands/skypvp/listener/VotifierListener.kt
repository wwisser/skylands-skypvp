package me.skylands.skypvp.listener

import com.vexsoftware.votifier.model.VotifierEvent
import me.skylands.skypvp.Messages
import me.skylands.skypvp.item.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*
import kotlin.collections.HashMap

class VotifierListener : Listener {

    companion object {
        val voteTimeStamps: MutableMap<UUID, Long> = HashMap()

        fun hasVoteKit(player: Player): Boolean {
            val time = voteTimeStamps.getOrDefault(player.uniqueId, System.currentTimeMillis())
            return System.currentTimeMillis() < time
        }
    }

    @EventHandler
    fun onVote(voteEvent: VotifierEvent) {
        val player = Bukkit.getPlayerExact(voteEvent.vote.username) ?: return

        Bukkit.broadcastMessage(Messages.PREFIX_LONG)
        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage("   §7Der Spieler §e" + player.name + " §7hat gevotet!")
        Bukkit.broadcastMessage("   §7Seine Belohnung:")
        Bukkit.broadcastMessage("    §8- §a35 Level")
        Bukkit.broadcastMessage("    §8- §a6 Stunden Eisen Auto-Equip")
        Bukkit.broadcastMessage("    §8- §a3x Vote-Crate Schlüssel")
        Bukkit.broadcastMessage("   §7Du willst auch eine Belohnung? §8§l=> §e/vote")
        Bukkit.broadcastMessage("")
        Bukkit.broadcastMessage(Messages.PREFIX_LONG)

        player.inventory.addItem(
            ItemBuilder(Material.TRIPWIRE_HOOK)
                .enchant(Enchantment.LOOT_BONUS_BLOCKS, 1)
                .name("§9§lVoteschlüssel").modifyLore().add("")
                .add("§7Verwende den Schlüssel am Spawn.").finish().glow().amount(3).build()
        )

        player.level += 35
        player.playSound(player.location, Sound.LEVEL_UP, Float.MAX_VALUE, Float.MAX_VALUE)
        voteTimeStamps[player.uniqueId] = System.currentTimeMillis() + 1000L * 60 * 60 * 6
    }



}