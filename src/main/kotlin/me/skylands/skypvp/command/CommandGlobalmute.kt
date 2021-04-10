package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender

class CommandGlobalmute : AbstractCommand() {

    companion object {
        @Volatile
        var globalmute = false
    }

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.globalmute")

        globalmute = globalmute xor true

        Bukkit.broadcastMessage(
            Messages.PREFIX + "Der Chat wurde "
                    + (if (globalmute) "§cdeaktiviert" else "§aaktiviert")
                    + "§7."
        )
    }

    override fun getName(): String {
        return "globalmute"
    }
}