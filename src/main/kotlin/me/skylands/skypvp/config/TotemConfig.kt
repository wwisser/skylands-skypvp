package me.skylands.skypvp.config

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.pve.Totem
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.EntityType

class TotemConfig {

    companion object {
        private const val FILE_NAME = "totems.yml"
    }

    private val config = Config(SkyLands.CONFIG_PATH, FILE_NAME)
    val totems: MutableMap<String, Totem> = HashMap()
    val bossTotems: MutableMap<String, Totem> = HashMap()

    init {
        val keys = config.getKeys(false)
        for(key in keys) {
            totems[key] = Totem(
                getCenterLocation(key),
                getDifficulty(key),
                getSpawnRadius(key),
                getEnemiesList(key),
                getBossType(key),
                getTotemIdentifer(key)
            )
            if (!totems[key]?.bossType.equals("None")) {
                bossTotems[key] = totems[key]!!
            }
        }
    }

    fun getCenterLocation(id: String): Location {
        return Location(
            Bukkit.getWorld("Map"),
            config["$id.centerlocation.x"] as Double,
            config["$id.centerlocation.y"] as Double,
            config["$id.centerlocation.z"] as Double
        )
    }

    private fun getDifficulty(id: String): Int {
        return config.getInt("$id.difficulty")
    }

    private fun getSpawnRadius(id: String): Int {
        return config.getInt("$id.spawnRadius")
    }

    private fun getEnemiesList(name: String): List<EntityType> {
        val temp = config.getStringList("$name.enemies")
        val list = emptyList<EntityType>().toMutableList()
        temp.forEach {
            list.add(EntityType.fromName(it))
        }
        return list.toList()
    }

    private fun getBossType(id: String) : String {
        return config.getString("$id.bossType")
    }

    private fun getTotemIdentifer(id: String) : Int {
        return config.getInt("$id.totemIdentifier")
    }
}