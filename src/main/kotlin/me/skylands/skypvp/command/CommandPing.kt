package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.command.CommandSender
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer

class CommandPing : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        val player = ValidateCommand.onlyPlayer(sender)
        var ping = (player as CraftPlayer).handle.ping

        if (ping < 1 || ping > 999) {
            ping = 999
        }

        player.sendMessage(Messages.PREFIX + "Dein Ping liegt bei §e" + ping + "ms§7.")
    }

    override fun getName(): String {
        return "ping"
    }
}