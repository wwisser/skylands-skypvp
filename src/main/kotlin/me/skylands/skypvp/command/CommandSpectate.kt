package me.skylands.skypvp.command

import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import org.bukkit.GameMode
import org.bukkit.command.CommandSender

class CommandSpectate : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.TEAM)
        ValidateCommand.minArgs(1, args, "§c/spectate <target>")

        val player = ValidateCommand.onlyPlayer(sender)

        if (args[0].equals("-u", ignoreCase = true)) {
            player.teleport(SkyLands.LOCATION_SPAWN)
            player.gameMode = GameMode.SURVIVAL
            player.chat("/vanish")
            return
        }

        val target = ValidateCommand.target(args[0], player)

        player.chat("/vanish")
        player.chat("/gamemode 3")
        player.chat("/tp " + target.name)
    }

    override fun getName(): String {
        return "spectate"
    }

}