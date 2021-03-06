package me.skylands.skypvp.command

import me.skylands.skypvp.command.exception.*
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import kotlin.jvm.Throws

object ValidateCommand {

    @Throws(PermissionException::class)
    fun permission(sender: CommandSender, permission: String) {
        if (!sender.hasPermission(permission)) {
            throw PermissionException()
        }
    }

    @Throws(InvalidArgsException::class)
    fun minArgs(min: Int, args: Array<String>, commandUsage: String) {
        if (args.size < min) {
            throw InvalidArgsException(commandUsage)
        }
    }

    /**
     * Must be a value over 0.
     */
    @Throws(InvalidAmountException::class)
    fun amount(argument: String): Int {
        return try {
            val amount = argument.toInt()
            if (amount > 0) {
                amount
            } else {
                throw InvalidAmountException(argument)
            }
        } catch (e: NumberFormatException) {
            throw InvalidAmountException(argument)
        }
    }

    /**
     * Must be a positive value.
     */
    @Throws(InvalidAmountException::class)
    fun positiveNumber(argument: String): Int {
        return try {
            val amount = argument.toInt()
            if (amount > -1) {
                amount
            } else {
                throw InvalidAmountException(argument)
            }
        } catch (e: NumberFormatException) {
            throw InvalidAmountException(argument)
        }
    }

    @Throws(InvalidSenderException::class)
    fun onlyPlayer(sender: CommandSender): Player {
        if (sender !is Player) {
            throw InvalidSenderException()
        }
        return sender
    }

    @Throws(CommandException::class)
    fun target(target: String, sender: CommandSender): Player {
        val targetPlayer = targetOrSelf(target)
        if (targetPlayer === sender) {
            throw SelfInteractionException()
        }
        return targetPlayer
    }

    @Throws(CommandException::class)
    fun targetOrSelf(target: String): Player {
        val targetPlayer = Bukkit.getPlayer(target)
        if (targetPlayer == null || !targetPlayer.isOnline) {
            throw TargetNotFoundException(target)
        }
        return targetPlayer
    }

    @Throws(HeldItemNotFoundException::class)
    fun heldItem(player: Player): ItemStack {
        val itemInHand = player.itemInHand
        if (itemInHand == null || itemInHand.type == Material.AIR) {
            throw HeldItemNotFoundException()
        }
        return itemInHand
    }

}