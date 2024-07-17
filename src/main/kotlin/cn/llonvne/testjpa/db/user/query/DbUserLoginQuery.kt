package cn.llonvne.testjpa.db.user.query

/**
 * Represent a Login query of [cn.llonvne.testjpa.db.user.DbUser]
 */
data class DbUserLoginQuery(
    val username: String,
    val password: String
) : DbUserQuery