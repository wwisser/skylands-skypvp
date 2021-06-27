package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.command.exception.CommandException
import org.bukkit.command.CommandSender

class CommandSetstats : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.ADMIN)
        ValidateCommand.minArgs(3, args, "/setstats <target> <kills|deaths|killstreak|blutpunkte> <amount>")
        val target = ValidateCommand.targetOrSelf(args[0])
        val amount = ValidateCommand.amount(args[2])

        val userService = SkyLands.userService
        val user = userService.getUser(target)

        when (args[1]) {
            "kills" -> user.kills = amount
            "deaths" -> user.deaths = amount
            "killstreak" -> user.currentKillstreak = amount
            "blutpunkte" -> user.bloodPoints = amount
            "blocke" -> user.blocksPlaced = amount
            else -> throw CommandException("Invalide Metrik: <kills|deaths|killstreak|blutpunkte>")
        }

        userService.saveUser(user)

        sender.sendMessage(Messages.PREFIX + "§aUserdaten erfolgreich aktualisiert")
    }

    override fun getName(): String {
        return "setstats"
    }
}