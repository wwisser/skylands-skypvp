package me.skylands.skypvp

import org.bukkit.entity.Player

object Messages {
    const val PREFIX = "§7[§6§lSL§7] §7"
    const val PREFIX_LONG = "§6§l§m@§8§l§m=============================§6§l§m@"
    const val NO_PERMISSION: String = "§cDu hast keinen Zugriff auf diesen Befehl."
    const val SELF_INTERACT: String = "§cDu darfst nicht mit dir selbst interagieren."
    const val NOT_A_PLAYER: String = "§cDiesen Befehl dürfen nur Spieler verwenden."
    const val NO_HELD_ITEM: String = "§cDu musst ein Item in deiner Hand halten."

    fun getVoteUrl(player: Player): String {
        return "https://www.serverliste.net/vote/1173/" + player.name
    }
}