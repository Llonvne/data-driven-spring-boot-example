package cn.llonvne.example.db.user.mutation

/**
 * Represent a mutation that insert a User
 */
data class DbUserNewMutation(
    val username: String,
    val password: String
) : DbUserMutation