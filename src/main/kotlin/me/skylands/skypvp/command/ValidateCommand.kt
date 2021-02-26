package me.skylands.skypvp.command

import me.skylands.skypvp.command.exception.PermissionException
import org.bukkit.command.CommandSender
import kotlin.jvm.Throws

class ValidateCommand {

    companion object {

        @Throws(PermissionException::class)
        fun permission(sender: CommandSender, permission: String?) {
            if (!sender.hasPermission(permission)) {
                throw PermissionException()
            }
        }

    }

}