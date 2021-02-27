package me.skylands.skypvp.user

interface UserRepository {
    fun fetchByUuid(uuid: String): User
    fun fetchByName(name: String): User
    fun fetchByColumn(column: Int): Map<String, Number>
    fun fetchNameByUuid(uuid: String): String
    fun save(user: User)
}
