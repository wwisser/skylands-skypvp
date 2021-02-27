package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.config.DiscoConfig
import me.skylands.skypvp.util.LocationUtils
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.command.CommandSender

class CommandDisco : AbstractCommand() {

    private val config: DiscoConfig = SkyLands.discoConfig

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        ValidateCommand.permission(sender, Permissions.ADMIN)
        ValidateCommand.minArgs(1, args, "/disco s <id>")
        val player = ValidateCommand.onlyPlayer(sender)
        val block: Block = LocationUtils.getTargetBlock(player, 5)


        if (block.type != Material.WOOL) {
            player.sendMessage(Messages.PREFIX + "§cBitte schaue auf einen Wollblock!")
            return
        }

        this.config.setLocation(args[1], block.location)
        player.sendMessage(Messages.PREFIX + "§7Der Block mit der ID §e#" + args[1] + " §7wurde erstellt!")
    }

    override fun getName(): String {
        return "disco"
    }

}