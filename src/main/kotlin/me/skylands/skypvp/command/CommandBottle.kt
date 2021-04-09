package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.CommandSender

class CommandBottle : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.bottle")
        val player = ValidateCommand.onlyPlayer(sender)

        for (itemStack in player.inventory.contents) {
            if (itemStack != null && itemStack.type == Material.GLASS) {
                itemStack.type = Material.GLASS_BOTTLE
            }
        }

        player.updateInventory()
        player.sendMessage(Messages.PREFIX + "Dein Glas wurde zu §eFlaschen§7!")
        player.playSound(player.location, Sound.GLASS, 1f, 1f)
    }

    override fun getName(): String {
        return "bottle"
    }
}