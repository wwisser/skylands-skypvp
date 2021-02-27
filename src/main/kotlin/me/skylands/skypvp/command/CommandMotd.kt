package me.skylands.skypvp.command

import me.skylands.skypvp.Messages;
import me.skylands.skypvp.command.exception.CommandException
import me.skylands.skypvp.config.MotdConfig
import net.md_5.bungee.api.ChatColor
import org.bukkit.command.CommandSender
import kotlin.jvm.Throws

class CommandMotd(private val config: MotdConfig) : AbstractCommand() {

    companion object {
        private const val USAGE = "/motd <reload|header|footer> <Text>"
    }

    @Throws(CommandException::class)
    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, "skylands.motd")

        if (args.isEmpty()) {
            sender.sendMessage(Messages.PREFIX + USAGE)
            sender.sendMessage("")
            this.showMotd(sender)
            return
        }

        when (args[0].toLowerCase()) {
            "header" -> this.config.updateHeader(joinText(args))
            "footer" -> this.config.updateFooter(joinText(args))
            "reload" -> this.config.reloadConfig()
            else -> {
                sender.sendMessage(Messages.PREFIX + USAGE)
                return
            }
        }
        sender.sendMessage(Messages.PREFIX + "Du hast die §fMOTD §7erfolgreich aktualisiert")
        sender.sendMessage("")
        this.showMotd(sender)
    }

    private fun showMotd(sender: CommandSender) {
        sender.sendMessage("§7" + this.config.getHeader())
        sender.sendMessage("§7" + this.config.getFooter())
    }

    private fun joinText(args: Array<String>): String {
        return ChatColor.translateAlternateColorCodes(
            '&',
            java.lang.String.join(
                " ", *args.copyOfRange(1, args.size)
            )
        )
    }

    override fun getName(): String {
        return "motd"
    }

}
