package me.skylands.skypvp.command

import org.bukkit.command.CommandSender

class CommandHurensohn : AbstractCommand() {
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        sender.sendMessage("Du huansohn")
    }

    override fun getName(): String {
        return "hurensohn"
    }
}