package me.skylands.skypvp.util

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.user.User
import org.bukkit.entity.Player

object TransactionUtils {

    fun handleTransaction(sender: Player, receiver: User, receiverPlayer: Player?, amount: Int): Boolean {
        return if (amount > 0 && sender.level >= amount) {
            sender.level -= amount

            if (receiverPlayer != null) {
                receiverPlayer.level += amount
            }

            receiver.level += amount

            val userService = SkyLands.userService
            val senderUser = userService.getUser(sender)
            senderUser.level = sender.level

            userService.saveUser(senderUser)
            userService.saveUser(receiver)

            true
        } else {
            false
        }
    }

}