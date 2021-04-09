package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.command.CommandSender

class CommandCook : AbstractCommand() {

    companion object {
        private val COOKABLE_ITEMS = listOf(
            Material.RAW_BEEF,
            Material.PORK,
            Material.RAW_CHICKEN,
            Material.POTATO_ITEM,
            Material.RABBIT,
            Material.MUTTON
        )
    }

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.cook")
        val player = ValidateCommand.onlyPlayer(sender)

        for (itemStack in player.inventory.contents) {
            if (itemStack != null && COOKABLE_ITEMS.contains(itemStack.type)) {
                itemStack.typeId = itemStack.typeId + 1
            }
        }

        player.updateInventory()
        player.sendMessage(Messages.PREFIX + "Alle essbaren Items wurden §egekocht§7!")
        player.playSound(player.location, Sound.BURP, 1f, 1f)
    }

    override fun getName(): String {
        return "cook"
    }

}