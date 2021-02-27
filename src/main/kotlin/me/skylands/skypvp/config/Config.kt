package me.skylands.skypvp.config

import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration

import java.io.File
import java.io.IOException

class Config(path: String, filename: String) {

    val file: File = File(path, filename)
    var fileConfiguration: FileConfiguration

    init {
        fileConfiguration = YamlConfiguration.loadConfiguration(file)
    }

    fun setDefault(key: String?, value: Any?) {
        if (!this.contains(key)) {
            this[key] = value
            saveFile()
        }
    }

    fun saveFile() {
        try {
            fileConfiguration.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    operator fun contains(path: String?): Boolean {
        return fileConfiguration.contains(path)
    }

    operator fun set(path: String?, value: Any?) {
        fileConfiguration[path] = value
    }

    operator fun get(path: String?): Any {
        return fileConfiguration[path]
    }

    operator fun get(path: String?, def: Any?): Any {
        return fileConfiguration[path, def]
    }

    fun getString(path: String?): String {
        return fileConfiguration.getString(path)
    }

    fun getInt(path: String?): Int {
        return fileConfiguration.getInt(path)
    }

    fun getLong(path: String?): Long {
        return fileConfiguration.getLong(path)
    }

    fun getKeys(deep: Boolean): Set<String> {
        return fileConfiguration.getKeys(deep)
    }

    fun getList(path: String?): List<*> {
        return fileConfiguration.getList(path)
    }

    fun getStringList(path: String?): List<String> {
        return fileConfiguration.getStringList(path)
    }

    fun getIntegerList(path: String?): List<Int> {
        return fileConfiguration.getIntegerList(path)
    }

}