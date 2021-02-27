package me.skylands.skypvp.nms

import com.google.common.collect.ImmutableMap
import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class Tablist {

    companion object {

        fun send(player: Player, header: String, footer: String) {
            val packet = PacketPlayOutPlayerListHeaderFooter()
            ImmutableMap
                .of("a", header, "b", footer)
                .forEach { (fieldName: String?, line: String?) ->
                    try {
                        val field = packet.javaClass.getDeclaredField(fieldName)
                        field.isAccessible = true
                        field[packet] = IChatBaseComponent.ChatSerializer
                            .a("{\"text\":\"" + ChatColor.translateAlternateColorCodes('&', line) + "\"}")
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }

    }

}