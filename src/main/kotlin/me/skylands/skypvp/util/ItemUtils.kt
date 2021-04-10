package me.skylands.skypvp.util

import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

object ItemUtils {

    fun addAndDropRest(player: Player, vararg itemStacks: ItemStack?) {
        player.inventory.addItem(*itemStacks).values.forEach(Consumer { itemToDrop: ItemStack ->
            player.world.dropItem(
                player.location,
                itemToDrop
            )
        })
    }


}