package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import org.bukkit.command.CommandSender
import java.util.*

class CommandCmdspy : AbstractCommand() {

    companion object {
        val commandSpy: MutableList<UUID> = ArrayList()
    }

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        val player = ValidateCommand.onlyPlayer(sender)
        ValidateCommand.permission(sender, Permissions.ADMIN)

        if (commandSpy.contains(player.uniqueId)) {
            commandSpy.remove(player.uniqueId)
            player.sendMessage(Messages.PREFIX + "CMD-Spy wurde §cdeaktiviert§7.")
        } else {
            commandSpy.add(player.uniqueId)
            player.sendMessage(Messages.PREFIX + "CMD-Spy wurde §aaktiviert§7.")
        }
    }

    override fun getName(): String {
        return "cmdspy"
    }

}