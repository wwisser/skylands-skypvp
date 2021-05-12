package me.skylands.skypvp.item

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object LightArtifactItemFactory {

    fun createLightArtifactItem(): ItemStack? {
        return ItemBuilder(Material.GHAST_TEAR)
                .name(ChatColor.WHITE.toString() + "Artifakt des " + ChatColor.YELLOW.toString() + "Lichts")
                .modifyLore()
                .add("§7Ein sagenumwobenes §eLichtartifakt§7!")
                .add("§7Behalte dein Inventar einmalig beim Tod.")
                .finish()
                .glow()
                .build()
    }

}