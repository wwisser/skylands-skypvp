package me.skylands.skypvp.user

import kotlin.math.roundToLong

data class User(
    var name: String,
    var uuid: String,
    var firstSeen: Long,
    var lastSeen: Long,
    var kills: Int,
    var currentKillstreak: Int,
    var bloodpoints : Int,
    var deaths: Int,
    var votes: Int,
    var playtime: Int,
    var level: Int
) {
    fun getKdr(): Double {
        if (kills != 0 && deaths != 0) {
            return (kills.toDouble() / deaths.toDouble() * 100.0).roundToLong() / 100.0
        }
        return if (kills != 0) {
            kills.toDouble()
        } else Double.NaN

    }

    fun increasePlaytime() {
        playtime++
    }
}
