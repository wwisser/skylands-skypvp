package me.skylands.skypvp.config

import me.skylands.skypvp.SkyLands
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.MutableList
import kotlin.collections.MutableMap
import kotlin.collections.set

class PeaceConfig {

    private var database: Config
    private var invites: MutableMap<String, MutableList<String>> = HashMap()

    init{
        database = Config(SkyLands.CONFIG_PATH, "peace.yml")
    }

    fun save() {
        database.saveFile()
    }

    fun setPeace(uuid1: String, uuid2: String) {
        val peaceList1 = getPeaceList(uuid1)
        val peaceList2 = getPeaceList(uuid2)
        peaceList1.add(uuid2)
        peaceList2.add(uuid1)
        database[uuid1] = peaceList1
        database[uuid2] = peaceList2
    }

    fun removePeace(uuid1: String, uuid2: String) {
        val peaceList1 = getPeaceList(uuid1)
        val peaceList2 = getPeaceList(uuid2)
        peaceList1.remove(uuid2)
        peaceList2.remove(uuid1)
        database[uuid1] = peaceList1
        database[uuid2] = peaceList2
    }

    fun setInvite(sender: String, target: String) {
        val invites = invites.getOrDefault(sender, ArrayList())
        invites.add(target)
        this.invites[sender] = invites
    }

    fun removeInvite(sender: String, target: String) {
        val invites = invites.getOrDefault(sender, ArrayList())
        invites.remove(target)
        this.invites[sender] = invites
    }

    fun hasInvite(acceptor: String, target: String): Boolean {
        return invites.getOrDefault(acceptor, ArrayList()).contains(target)
    }

    fun hasPeace(target: String, toCheck: String): Boolean {
        return getPeaceList(target).contains(toCheck)
    }

    fun getPeaceList(uuid: String): MutableList<String> {
        return if (!database.contains(uuid)) {
            mutableListOf()
        } else {
            database.getStringList(uuid).map { it }.toMutableList()
        }
    }

}