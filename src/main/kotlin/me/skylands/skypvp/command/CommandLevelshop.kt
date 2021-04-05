package me.skylands.skypvp.command

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.container.template.ContainerTemplate
import me.skylands.skypvp.container.template.impl.ShopContainerTemplate
import org.bukkit.command.CommandSender

class CommandLevelshop : AbstractCommand() {

    private val container: ContainerTemplate = ShopContainerTemplate(SkyLands.containerManager)

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        this.container.open(ValidateCommand.onlyPlayer(sender))
    }

    override fun getName(): String {
        return "levelshop"
    }

}