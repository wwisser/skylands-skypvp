package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class CommandRename : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.ADMIN)
        val player = ValidateCommand.onlyPlayer(sender)

        ValidateCommand.minArgs(1, args, "/rename <name>")
        val itemStack = ValidateCommand.heldItem(player)

        val itemMeta = itemStack.itemMeta
        val name = "§f" + java.lang.String.join(" ", *args)

        itemMeta.displayName = ChatColor.translateAlternateColorCodes('&', name)
        itemStack.itemMeta = itemMeta
        sender.sendMessage(Messages.PREFIX + "Item zu '§e" + name + "§7' umbenannt.")
    }

    override fun getName(): String {
        return "rename"
    }

}