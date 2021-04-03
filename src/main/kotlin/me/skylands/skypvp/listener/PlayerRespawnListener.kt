package me.skylands.skypvp.listener

import me.skylands.skypvp.item.ItemBuilder
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack


class PlayerRespawnListener : Listener {

    companion object {
        const val AUTO_KIT_NAME = "§6§lAutokit"
    }

    private val AUTO_KIT_VOTE: List<ItemStack> = listOf(
        ItemBuilder(Material.IRON_SWORD).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.COOKED_CHICKEN).amount(6).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.IRON_HELMET).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.IRON_CHESTPLATE).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.IRON_LEGGINGS).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.IRON_BOOTS).name(AUTO_KIT_NAME).build(),
    )

    private val AUTO_KIT_DEFAULT: List<ItemStack> = listOf(
        ItemBuilder(Material.STONE_SWORD).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.COOKED_CHICKEN).amount(6).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.CHAINMAIL_HELMET).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.CHAINMAIL_CHESTPLATE).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.CHAINMAIL_LEGGINGS).name(AUTO_KIT_NAME).build(),
        ItemBuilder(Material.CHAINMAIL_BOOTS).name(AUTO_KIT_NAME).build(),
    )

    @EventHandler
    fun onPlayerRespawn(event: PlayerRespawnEvent) {
        val player = event.player

        val autoKit: List<ItemStack> = if (VotifierListener.hasVoteKit(player)) AUTO_KIT_VOTE else AUTO_KIT_DEFAULT

        player.inventory.addItem(autoKit[0])
        player.inventory.addItem(autoKit[1])
        player.inventory.helmet = autoKit[2]
        player.inventory.chestplate = autoKit[3]
        player.inventory.leggings = autoKit[4]
        player.inventory.boots = autoKit[5]
        player.playSound(player.location, Sound.HORSE_SADDLE, 1f, 1f)
    }

}