package me.skylands.skypvp.user

import org.bukkit.entity.Player
import java.util.function.Predicate
import java.util.stream.Collectors

class UserService {
    private val onlineCache: MutableMap<Player, User> = HashMap()
    val userRepository: UserRepository = FileUserRepository()

    fun loadUser(player: Player): User {
        val fetchedUser = userRepository.fetchByUuid(player.uniqueId.toString())
        onlineCache[player] = fetchedUser
        return fetchedUser
    }

    fun saveUser(user: User) {
        userRepository.save(user)
    }

    fun unloadUser(player: Player) {
        val user = getUser(player)
        user.lastSeen = System.currentTimeMillis()
        userRepository.save(user)
        onlineCache.remove(player)
    }

    fun getUser(player: Player): User {
        return onlineCache[player]!!
    }

    fun getUserByName(name: String): User? {
        val result: List<User> = filterCache { it.name.equals(name, ignoreCase = true) }
        return if (result.isEmpty()) {
            try {
                userRepository.fetchByName(name)
            } catch (e: IllegalArgumentException) {
                null
            }
        } else {
            result[0]
        }
    }
    fun getUserByUuid(uuid: String): User {
        val result = filterCache { user: User -> user.uuid == uuid }
        return if (result.isEmpty()) {
            userRepository.fetchByUuid(uuid)
        } else {
            result[0]
        }
    }

    private fun filterCache(predicate: Predicate<User>): List<User> {
        return onlineCache.values
            .stream()
            .filter(predicate)
            .collect(Collectors.toList())
    }
}
