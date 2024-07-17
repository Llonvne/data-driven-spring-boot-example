package cn.llonvne.testjpa.db.user.query

/**
 * Represent a Login query of User domain
 */
data class DbUserLoginQuery(
    val username: String,
    val password: String
) : DbUserQuery