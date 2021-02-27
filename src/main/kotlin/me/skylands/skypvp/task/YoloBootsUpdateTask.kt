package me.skylands.skypvp.task

import me.skylands.skypvp.nms.Particles
import net.minecraft.server.v1_8_R3.EnumParticle
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.scheduler.BukkitRunnable
import java.util.concurrent.ThreadLocalRandom

class YoloBootsUpdateTask : BukkitRunnable() {

    // TODO refactor legacy code
    override fun run() {
        for (player in Bukkit.getOnlinePlayers()) {
            if (player.inventory.boots != null && player.inventory.boots.type == Material.LEATHER_BOOTS && player.inventory.boots.itemMeta != null && player.inventory.boots.itemMeta.displayName != null && player.inventory.boots.itemMeta.displayName.equals(
                    ChatColor.AQUA.toString() + "YOLO-Boots", ignoreCase = true
                )
            ) {

                Particles.play(player.location, EnumParticle.FIREWORKS_SPARK, 0, 0, 0, 0, 0)
                val boots = ItemStack(Material.LEATHER_BOOTS, 1)
                val bootsMeta = boots.itemMeta as LeatherArmorMeta
                when (randInt(1, 25)) {
                    1 -> {
                        bootsMeta.color = Color.fromRGB(255, 0, 0)
                    }
                    2 -> {
                        bootsMeta.color = Color.fromRGB(255, 255, 0)
                    }
                    3 -> {
                        bootsMeta.color = Color.fromRGB(76, 153, 0)
                    }
                    4 -> {
                        bootsMeta.color = Color.fromRGB(0, 255, 255)
                    }
                    5 -> {
                        bootsMeta.color = Color.fromRGB(153, 0, 153)
                    }
                    6 -> {
                        bootsMeta.color = Color.fromRGB(255, 255, 255)
                    }
                    7 -> {
                        bootsMeta.color = Color.fromRGB(255, 51, 153)
                    }
                    8 -> {
                        bootsMeta.color = Color.fromRGB(255, 128, 0)
                    }
                    9 -> {
                        bootsMeta.color = Color.fromRGB(224, 224, 224)
                    }
                    10 -> {
                        bootsMeta.color = Color.fromRGB(0, 0, 153)
                    }
                    11 -> {
                        bootsMeta.color = Color.fromRGB(47, 79, 79)
                    }
                    12 -> {
                        bootsMeta.color = Color.fromRGB(255, 215, 0)
                    }
                    13 -> {
                        bootsMeta.color = Color.fromRGB(255, 246, 143)
                    }
                    14 -> {
                        bootsMeta.color = Color.fromRGB(127, 255, 0)
                    }
                    15 -> {
                        bootsMeta.color = Color.fromRGB(0, 245, 255)
                    }
                    16 -> {
                        bootsMeta.color = Color.fromRGB(205, 193, 197)
                    }
                    17 -> {
                        bootsMeta.color = Color.fromRGB(0, 250, 154)
                    }
                    18 -> {
                        bootsMeta.color = Color.fromRGB(32, 178, 170)
                    }
                    19 -> {
                        bootsMeta.color = Color.fromRGB(248, 248, 255)
                    }
                    20, 21 -> {
                        bootsMeta.color = Color.fromRGB(240, 248, 255)
                    }
                    22 -> {
                        bootsMeta.color = Color.fromRGB(25, 25, 112)
                    }
                    23 -> {
                        bootsMeta.color = Color.fromRGB(110, 123, 139)
                    }
                    24 -> {
                        bootsMeta.color = Color.fromRGB(139, 69, 19)
                    }
                    25 -> {
                        bootsMeta.color = Color.fromRGB(132, 112, 255)
                    }
                    else -> {
                        bootsMeta.color = Color.fromRGB(153, 255, 153)
                    }
                }
                bootsMeta.displayName = ChatColor.AQUA.toString() + "YOLO-Boots"
                boots.itemMeta = bootsMeta
                player.inventory.boots = boots
            }
        }
    }


    private fun randInt(min: Int, max: Int): Int {
        return ThreadLocalRandom.current().nextInt(max - min + 1) + min
    }

}