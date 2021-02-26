package me.skylands.skypvp.command

import me.skylands.skypvp.Permissions
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender

class CommandRawbc : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.ADMIN)
        Bukkit.broadcastMessage(
            ChatColor.translateAlternateColorCodes(
                '&',
                args.joinToString(" ")
            )
        )
    }

}