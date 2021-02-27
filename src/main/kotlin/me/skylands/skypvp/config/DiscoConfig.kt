package me.skylands.skypvp.config

import me.skylands.skypvp.SkyLands
import org.bukkit.Bukkit
import org.bukkit.Effect
import org.bukkit.Location
import org.bukkit.World
import java.util.*

class DiscoConfig {

    companion object {
        private const val FILE_NAME = "disco.yml"
    }

    val locations: Map<String, Location> = HashMap()
    private val config = Config(SkyLands.CONFIG_PATH, FILE_NAME)

    init {
        val keys: Set<String> = this.config.getKeys(false)

        for (key in keys) {
            this.locations.put(key, getLocation(key))
            this.getLocation(key).world.playEffect(getLocation(key), Effect.STEP_SOUND, 35)
        }
    }

    fun getLocation(name: String): Location {
        val x: Int = this.config.getInt("$name.X")
        val y: Int = this.config.getInt("$name.Y")
        val z: Int = this.config.getInt("$name.Z")
        val world: World = Bukkit.getWorld(this.config.getString("$name.world"))

        return Location(world, x.toDouble(), y.toDouble(), z.toDouble())
    }

    fun setLocation(name: String, location: Location) {
        this.config["$name.X"] = location.blockX
        this.config["$name.Y"] = location.blockY
        this.config["$name.Z"] = location.blockZ
        this.config["$name.world"] = location.world.name
    }

    fun save() {
        this.locations.forEach { this.setLocation(it.key, it.value) }
        this.config.saveFile()
    }

}