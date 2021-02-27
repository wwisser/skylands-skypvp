package me.skylands.skypvp.item

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

class YoloBootsItemFactory {

    companion object {
        fun createYoloBootsItem(): ItemStack? {
            return ItemBuilder(Material.LEATHER_BOOTS)
                .name(ChatColor.AQUA.toString() + "YOLO-Boots")
                .modifyLore()
                .add("§7Die offiziellen §6§lSkyLands §7Boots.")
                .add("§7Anziehen, um die Effekte zu aktivieren.")
                .finish()
                .build()
        }
    }

}