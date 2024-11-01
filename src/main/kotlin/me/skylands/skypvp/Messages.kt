package me.skylands.skypvp

object Messages {
    const val PREFIX = "§7[§6§lSL§7] §7"
    const val PREFIX_LONG = "§6§l§m@§8§l§m=============================§6§l§m@"
    const val PREFIX_LONG_CENTER = "§6§l§m@§8§l§m===================================§6§l§m@"

    const val NO_PERMISSION: String = "§cDu hast keinen Zugriff auf diesen Befehl."
    const val SELF_INTERACT: String = "§cDu darfst nicht mit dir selbst interagieren."
    const val NOT_A_PLAYER: String = "§cDiesen Befehl dürfen nur Spieler verwenden."
    const val NO_HELD_ITEM: String = "§cDu musst ein Item in deiner Hand halten."

    fun getVoteUrl(name: String): String {
        return "https://minecraft-server.eu/vote/index/21FF4/$name"
    }
}