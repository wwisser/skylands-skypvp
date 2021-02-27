package me.skylands.skypvp.command

import me.skylands.skypvp.Messages
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.command.exception.SelfInteractionException
import me.skylands.skypvp.command.exception.TargetNotFoundException
import me.skylands.skypvp.config.PeaceConfig
import me.skylands.skypvp.user.User
import me.skylands.skypvp.user.UserService
import org.bukkit.Bukkit
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class CommandPeace : AbstractCommand() {

    private var config: PeaceConfig = SkyLands.peaceConfig
    private var userService: UserService = SkyLands.userService

    override fun process(sender: CommandSender, label: String, args: Array<String>) {
        val player = ValidateCommand.onlyPlayer(sender)
        val uuid = player.uniqueId.toString()

        if (args.size == 0) {
            val peaceList: List<String> = this.config.getPeaceList(uuid)
            if (peaceList.isEmpty()) {
                player.sendMessage(Messages.PREFIX + "§cDu hast mit niemandem Frieden geschlossen.")
            } else {
                player.sendMessage(Messages.PREFIX + "Du hast mit §e" + peaceList.size + " §7Spielern Frieden geschlossen.")
            }
            for (peaceUuid in peaceList) {
                player.sendMessage(" §8- §e" + this.userService.getUserByUuid(peaceUuid).name)
            }
            player.sendMessage(" §7Schließe/Beende Frieden mit §e/friede <Name>")
        } else if (args.size == 1) {
            val user: User? = this.userService.getUserByName(args[0])
            if (user == null) {
                throw TargetNotFoundException(args[0])
            } else if (user.name == player.name) {
                throw SelfInteractionException()
            }
            val target: Player? = Bukkit.getPlayer(user.name)
            if (this.config.hasPeace(uuid, user.uuid)) {
                this.config.removePeace(uuid, user.uuid)
                player.sendMessage(Messages.PREFIX + "§cDu hast den Frieden mit §e" + user.name + " §caufgelöst.")
                if (target != null && target.isOnline) {
                    target.sendMessage(Messages.PREFIX + "§e" + player.name + " §chat den Frieden mit dir aufgelöst.")
                }
            } else {
                if (target == null || !target.isOnline) {
                    throw TargetNotFoundException(user.name)
                }
                if (this.config.hasInvite(uuid, user.uuid)) {
                    this.config.removeInvite(uuid, target.uniqueId.toString())
                    this.config.setPeace(uuid, target.uniqueId.toString())
                    Bukkit.broadcastMessage(Messages.PREFIX + "§e" + player.name + " §7hat mit §e" + target.name + " §7Frieden geschlossen.")
                    return
                }
                if (this.config.hasInvite(user.uuid, uuid)) {
                    player.sendMessage(Messages.PREFIX + "§cDu hast diesem Spieler bereits Frieden angeboten.")
                    return
                }
                this.config.setInvite(user.uuid, uuid)
                player.sendMessage(Messages.PREFIX + "Du hast §e" + target.name + " §7Frieden angeboten.")
                target.sendMessage(Messages.PREFIX + "§e" + player.name + " §7bietet dir Frieden an!")
                target.sendMessage(" §7Verwende §e/friede accept " + player.name + "§7, um anzunehmen.")
            }
        } else if (args[0].equals("accept", ignoreCase = true)) {
            val target = ValidateCommand.target(args[1], sender)
            if (this.config.hasInvite(uuid, target.uniqueId.toString())) {
                this.config.removeInvite(uuid, target.uniqueId.toString())
                this.config.setPeace(uuid, target.uniqueId.toString())
                Bukkit.broadcastMessage(Messages.PREFIX + "§e" + player.name + " §7hat mit §e" + target.name + " §7Frieden geschlossen.")
            } else {
                player.sendMessage(Messages.PREFIX + "§cDu hast keine Anfrage von §e" + target.name + " §cbekommen.")
            }
        }
    }

    override fun getName(): String {
        return "peace"
    }

}