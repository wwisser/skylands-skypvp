package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandClearchat : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.TEAM)

        for (i in 0..299) {
            var message = ""
            if (i <= 150) {
                message = "§0" + Math.random().toString()
            }
            val finalMessage = message
            Bukkit.getOnlinePlayers()
                .stream()
                .filter { player: Player -> !player.hasPermission(Permissions.TEAM) }
                .forEach { player: Player -> player.sendMessage(finalMessage) }
        }

        Bukkit.broadcastMessage(Messages.PREFIX + "Der Chat wurde von §e" + sender.name + " §7geleert.")
    }

    override fun getName(): String {
        return "clearchat"
    }

}