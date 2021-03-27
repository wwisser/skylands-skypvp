package me.skylands.skypvp.stats

import org.bukkit.entity.Player

object LastHitCache {

    val lastHits: MutableMap<Player, Player> = HashMap()

}