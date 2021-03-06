package me.skylands.skypvp.util

import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player
import org.bukkit.util.BlockIterator

object LocationUtils {

    fun getTargetBlock(player: Player, range: Int): Block {
        val blockIterator = BlockIterator(player, range)
        var lastBlock = blockIterator.next()
        while (blockIterator.hasNext()) {
            lastBlock = blockIterator.next()
            if (lastBlock.type == Material.AIR) {
                continue
            }
            break
        }
        return lastBlock
    }

}