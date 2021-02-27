package me.skylands.skypvp.nms

import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class Particles {

    companion object {

        fun play(
            player: Player,
            location: Location,
            particle: EnumParticle,
            xoff: Float,
            zoff: Float,
            yoff: Float,
            speed: Float,
            amount: Int
        ) {
            val x = location.x.toFloat()
            val y = location.y.toFloat()
            val z = location.z.toFloat()
            val packet = PacketPlayOutWorldParticles(
                particle, true, x, y, z, xoff, yoff, zoff, speed, amount
            )
            (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }

        fun play(
            location: Location,
            particle: EnumParticle,
            xoff: Float,
            zoff: Float,
            yoff: Float,
            speed: Float,
            amount: Int
        ) {
            Bukkit.getOnlinePlayers().forEach { play(it, location, particle, xoff, zoff, yoff, speed, amount) }
        }

    }

}
