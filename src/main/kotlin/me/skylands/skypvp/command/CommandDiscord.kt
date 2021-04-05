package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import org.bukkit.command.CommandSender

class CommandDiscord : AbstractCommand() {

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        sender.sendMessage(Messages.PREFIX + "Trete der Community bei: §ehttp://discord.skylands.me")
    }

    override fun getName(): String {
        return "discord"
    }

}