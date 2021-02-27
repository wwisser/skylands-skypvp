package me.skylands.skypvp

import me.skylands.skypvp.command.AbstractCommand
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin

class SkyLands : JavaPlugin() {

    override fun onEnable() {
        PackageClassIndexer.resolveInstances("me.skylands.skypvp.listener", Listener::class.java)
            .forEach { super.getServer().pluginManager.registerEvents(it, this) }

        PackageClassIndexer.resolveInstances("me.skylands.skypvp.commands", AbstractCommand::class.java)
            .forEach { super.getCommand(it.getName()).executor = it }
    }

}