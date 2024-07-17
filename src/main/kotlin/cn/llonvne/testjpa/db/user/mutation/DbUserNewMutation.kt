package cn.llonvne.testjpa.db.user.mutation

/**
 * Represent a insert mutation of [cn.llonvne.testjpa.db.user.DbUser]
 */
data class NewDbUserMutation(
    val username: String,
    val password: String
) : DbUserMutation