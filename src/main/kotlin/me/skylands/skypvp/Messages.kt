package me.skylands.skypvp

interface Messages {
    companion object {
        const val PREFIX = "§7[§6§lSL§7] §7"
        const val NO_PERMISSION: String = "§cDu hast keinen Zugriff auf diesen Befehl."
        const val SELF_INTERACT: String = "§cDu darfst nicht mit dir selbst interagieren."
        const val NOT_A_PLAYER: String = "§cDiesen Befehl dürfen nur Spieler verwenden."
        const val NO_HELD_ITEM: String = "§cDu musst ein Item in deiner Hand halten."
    }
}