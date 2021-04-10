package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.util.ItemUtils
import org.bukkit.Bukkit
import org.bukkit.Sound
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandGiveall : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.ADMIN)

        val player = ValidateCommand.onlyPlayer(sender)
        val itemStack = ValidateCommand.heldItem(player)
s
        Bukkit.getOnlinePlayers()
            .stream()
            .filter { players: Player -> players !== player }
            .forEach { players: Player ->
                ItemUtils.addAndDropRest(players, itemStack)
                players.playSound(players.location, Sound.HORSE_SADDLE, 1f, 1f)
            }

        Bukkit.broadcastMessage(
            Messages.PREFIX + "Jeder Spieler hat "
                    + itemStack.amount
                    + "x §e"
                    + itemStack.type.toString()
                    + " §7erhalten"
        )
    }

    override fun getName(): String {
        return "giveall"
    }

}