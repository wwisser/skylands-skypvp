package me.skylands.skypvp.user

import kotlin.math.roundToLong

data class User(
        var name: String,
        var uuid: String,
        var firstSeen: Long,
        var lastSeen: Long,
        var kills: Int,
        var currentKillstreak: Int,
        var bloodPoints : Int,
        var deaths: Int,
        var votes: Int,
        var playtime: Int,
        var level: Int,
        var hasIslandEffectHaste: Boolean,
        var hasIslandEffectSpeed: Boolean,
        var hasIslandEffectWaterBreathing: Boolean,
        var hasReducedEnchantingCostsUpgrade: Boolean,
        var damageReductionLevel: Int,
        var increasedMobDamageLevel: Int,
        var blocksPlaced: Int,
        var spidersKilled: Int,
        var mobsKilled: Int,
        var woodChopped: Int,
        var teamWorkChallengeCompleted: Boolean,
        var islandLevelChallengeCompleted: Boolean
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
