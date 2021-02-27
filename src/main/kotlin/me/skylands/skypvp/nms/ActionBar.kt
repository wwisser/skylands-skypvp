package me.skylands.skypvp.nms

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBar {

    companion object {
        fun send(message: String, sender: CommandSender) {
            if (sender is Player) {
                val playerConnection = (sender as CraftPlayer).handle.playerConnection
                val chatComponent = IChatBaseComponent.ChatSerializer.a("{\"text\":\"$message\"}")
                playerConnection.sendPacket(PacketPlayOutChat(chatComponent, 2.toByte()))
            } else {
                sender.sendMessage(message)
            }
        }
    }

}