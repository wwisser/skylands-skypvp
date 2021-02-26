package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.command.exception.CommandException
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import kotlin.jvm.Throws

abstract class AbstractCommand : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        try {
            this.process(sender, label, args)
        } catch (e: Exception) {
            var message = e.message
            message = if (e is CommandException) {
                Messages.PREFIX + "§c" + e.message
            } else {
                Messages.PREFIX + "§c" + e.javaClass.simpleName + ": §o" + message
            }
            sender.sendMessage(message)
        }
        return true
    }

    @Throws(CommandException::class)
    abstract fun process(sender: CommandSender, label: String, args: Array<String>)

}