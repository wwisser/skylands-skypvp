package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandTeamchat : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.onlyPlayer(sender)
        ValidateCommand.permission(sender, Permissions.TEAM)
        ValidateCommand.minArgs(1, args, "/tc <message>")

        val player = sender as Player
        val message = java.lang.String.join(" ", *args.copyOfRange(0, args.size))

        Bukkit.getOnlinePlayers()
            .stream()
            .filter { it.hasPermission(Permissions.TEAM) }
            .forEach { receiver: Player -> receiver.sendMessage(Messages.PREFIX + "§a" + player.name + "§7: §f" + message) }

    }

    override fun getName(): String {
        return "teamchat"
    }

}