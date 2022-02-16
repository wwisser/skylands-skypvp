package me.skylands.skypvp.user

import me.skylands.skypvp.SkyLands
import me.skylands.skypvp.config.Config
import org.bukkit.entity.Player

class FileUserRepository : UserRepository {
    private val userDatabase: Config = Config(SkyLands.CONFIG_PATH, FILE_NAME_USERS)
    private val uuidDatabase: Config = Config(SkyLands.CONFIG_PATH, FILE_NAME_UUIDS)

    override fun fetchByUuid(uuid: String): User {
        val name: String = uuidDatabase.getString(uuid)
        val currentTimeMillis = System.currentTimeMillis()
        val islandEffectHasteStatus = false
        val islandEffectSpeedStatus = false
        val islandEffectWaterBreathingStatus = false
        val hasReducedEnchantingCostsUpgrade = false
        val islandLevelChallengeCompleted = false
        val teamworkChallengeCompleted = false
        val witchUnlocked = false

        if (!userDatabase.contains(uuid)) {
            return User(
                name,
                uuid,
                currentTimeMillis,
                currentTimeMillis,
                0,
                0,
                0,
                0,
                0,
                0,
                0,
                islandEffectHasteStatus,
                islandEffectSpeedStatus,
                islandEffectWaterBreathingStatus,
                hasReducedEnchantingCostsUpgrade,
                0,
                0,
                0,
                0,
                0,
                0,
                teamworkChallengeCompleted,
                islandLevelChallengeCompleted,
                witchUnlocked
            )
        }
        val data: List<String> = userDatabase.getString(uuid).split(";")
        return User(
            name,
            uuid,
            java.lang.Long.valueOf(data[0]),
            java.lang.Long.valueOf(data[1]),
            Integer.valueOf(data[2]),
            Integer.valueOf(data[3]),
            Integer.valueOf(data[4]),
            Integer.valueOf(data[5]),
            Integer.valueOf(data[6]),
            Integer.valueOf(data[7]),
            Integer.valueOf(data[8]),
            data[9].toBoolean(),
            data[10].toBoolean(),
            data[11].toBoolean(),
            data[12].toBoolean(),
            Integer.valueOf(data[13]),
            Integer.valueOf(data[14]),
            Integer.valueOf(data[15]),
            Integer.valueOf(data[16]),
            Integer.valueOf(data[17]),
            Integer.valueOf(data[18]),
            data[19].toBoolean(),
            data[20].toBoolean(),
            data[21].toBoolean()
        )
    }

    override fun fetchByName(name: String): User {
        val uuid: String = uuidDatabase.getString(name)
        return this.fetchByUuid(uuid)
    }

    override fun fetchByColumn(column: Int): Map<String, Number> {
        val result: MutableMap<String, Number> = HashMap()
        for (key in userDatabase.getKeys(false)) {
            val number: Number = java.lang.Long.valueOf(userDatabase.getString(key).split(";").get(column))
            result[uuidDatabase.getString(key)] = number
        }
        return result
    }

    override fun fetchNameByUuid(uuid: String): String {
        return uuidDatabase.getString(uuid)
    }

    override fun save(user: User) {
        val data: String = (user.firstSeen.toString() + ";"
                + user.lastSeen + ";"
                + user.kills + ";"
                + user.currentKillstreak + ";"
                + user.bloodPoints + ";"
                + user.deaths + ";"
                + user.votes + ";"
                + user.playtime + ";"
                + user.level + ";"
                + user.hasIslandEffectHaste + ";"
                + user.hasIslandEffectSpeed + ";"
                + user.hasIslandEffectWaterBreathing + ";"
                + user.hasReducedEnchantingCostsUpgrade + ";"
                + user.damageReductionLevel + ";"
                + user.increasedMobDamageLevel + ";"
                + user.blocksPlaced + ";"
                + user.spidersKilled + ";"
                + user.mobsKilled + ";"
                + user.woodChopped + ";"
                + user.teamWorkChallengeCompleted + ";"
                + user.islandLevelChallengeCompleted + ";"
                + user.witchUnlocked)
        userDatabase[user.uuid] = data
        userDatabase.saveFile()
    }

    fun updateUuid(player: Player) {
        val name = player.name
        val uuid = player.uniqueId.toString()
        uuidDatabase[name] = uuid
        uuidDatabase[uuid] = name
        uuidDatabase.saveFile()
    }

    companion object {
        private const val FILE_NAME_USERS = "users.yml"
        private const val FILE_NAME_UUIDS = "uuids.yml"
    }
}