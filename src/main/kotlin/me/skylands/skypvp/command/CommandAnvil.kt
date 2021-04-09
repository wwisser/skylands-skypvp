package me.skylands.skypvp.command

import net.minecraft.server.v1_8_R3.*
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer

class CommandAnvil : AbstractCommand() {

    private class MockAnvilContainer internal constructor(entity: EntityHuman) : ContainerAnvil(
        entity.inventory,
        entity.world,
        BlockPosition(entity.locX, entity.locY, entity.locZ),
        entity
    ) {
        override fun a(entityhuman: EntityHuman): Boolean {
            return true
        }
    }

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.anvil")
        val player = ValidateCommand.onlyPlayer(sender)

        val entityPlayer = (player as CraftPlayer).handle
        val container = MockAnvilContainer(entityPlayer)

        val containerCount = entityPlayer.nextContainerCounter()
        entityPlayer.playerConnection.sendPacket(
            PacketPlayOutOpenWindow(
                containerCount,
                "minecraft:anvil",
                ChatMessage("Repairing"),
                0
            )
        )

        entityPlayer.activeContainer = container
        entityPlayer.activeContainer.windowId = containerCount
        entityPlayer.activeContainer.addSlotListener(entityPlayer)
        entityPlayer.activeContainer = container
    }

    override fun getName(): String {
        return "anvil"
    }

}