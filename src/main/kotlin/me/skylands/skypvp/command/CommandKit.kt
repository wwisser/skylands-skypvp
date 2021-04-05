package me.skylands.skypvp.command

import me.skylands.skypvp.container.legacy.InventoryKit
import org.bukkit.command.CommandSender

class CommandKit : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        InventoryKit.open(ValidateCommand.onlyPlayer(sender))
    }

    override fun getName(): String {
        return "kit"
    }

}