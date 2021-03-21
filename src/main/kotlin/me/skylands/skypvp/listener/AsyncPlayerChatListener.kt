package me.skylands.skypvp.listener

import me.skylands.skypvp.Messages
import me.skylands.skypvp.Permissions
import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.delay.DelayConfig
import me.skylands.skypvp.delay.DelayService
import me.skylands.skypvp.user.UserService
import net.milkbowl.vault.chat.Chat
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.HashMap

class AsyncPlayerChatListener : Listener {

    companion object {
        private const val PREFIX_FORMAT = "§8[%s§8]"
        private const val MESSAGE_LENGTH_IGNORE_REPEAT = 3
        private val DELAY_CONFIGURATION: DelayConfig = DelayConfig(
            "Bitte warte noch %time.",
            TimeUnit.SECONDS.toMillis(1)
        )
    }

    private val sentMessages: MutableMap<Player, Deque<String>> = HashMap()

    private var userService: UserService = SkyLands.userService
    private var vaultChat: Chat = Bukkit.getServer().servicesManager.getRegistration(Chat::class.java).provider

    @EventHandler
    fun onAsyncPlayerChat(event: AsyncPlayerChatEvent) {
        val player = event.player
        val message = event.message
        val formattedMessage = (fetchPrefix(player) + " §7" + player.name + "§8: §r" + formatMessage(player, message))
        event.format = formattedMessage
        if (player.hasPermission(Permissions.TEAM)) {
            return
        }
        if (userService.getUser(player).playtime < 30) {
            event.isCancelled = true
            player.sendMessage(Messages.PREFIX+ "§cDu darfst erst ab 30 Minuten Spielzeit schreiben.")
            return
        }
        val delayed: Boolean = DelayService.handleDelayInverted(player, DELAY_CONFIGURATION) { sender ->
            event.isCancelled =
                true
            sender.sendMessage(Messages.PREFIX + "§cDu schreibst zu schnell.")
        }
        if (delayed || message.length <= MESSAGE_LENGTH_IGNORE_REPEAT) {
            return
        }
        val messages = sentMessages.getOrDefault(player, ArrayDeque())
        if (messages.stream().anyMatch { anotherString: String? -> message.equals(anotherString, ignoreCase = true) }) {
            if (!event.isCancelled) {
                player.sendMessage(formattedMessage)
            }
            event.isCancelled = true
            return
        }
        messages.addLast(message)
        if (messages.size > MESSAGE_LENGTH_IGNORE_REPEAT) {
            messages.removeFirst()
        }
        sentMessages[player] = messages
    }

    private fun fetchPrefix(player: Player): String {
        val playerPrefix = ChatColor.translateAlternateColorCodes(
            '&', vaultChat.getPlayerPrefix(player)
        )
        return String.format(PREFIX_FORMAT, playerPrefix)
    }

    private fun formatMessage(sender: Player, message: String): String {
        var formattedMessage = message
        if (sender.hasPermission("skylands.chat.color")) {
            formattedMessage = ChatColor.translateAlternateColorCodes('&', formattedMessage)
        }
        for (chatColor in ChatColor.values()) {
            if (sender.hasPermission("skylands.chatcolor." + chatColor.toString().toLowerCase())) {
                formattedMessage = formattedMessage.replace(
                    "&" + chatColor.char,
                    "§" + chatColor.char
                )
            }
        }
        return formattedMessage.replace("%", "%%")
    }

}