package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.CommandSender

class CommandFill : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.fill")
        val player = ValidateCommand.onlyPlayer(sender)

        for (itemStack in player.inventory.contents) {
            if (itemStack != null && itemStack.type == Material.GLASS_BOTTLE) {
                itemStack.type = Material.POTION
            }
        }

        player.updateInventory()
        player.sendMessage(Messages.PREFIX + "Deine Flaschen wurden §ebefüllt§7!")
        player.playSound(player.location, Sound.DRINK, 1f, 1f)
    }

    override fun getName(): String {
        return "fill"
    }

}