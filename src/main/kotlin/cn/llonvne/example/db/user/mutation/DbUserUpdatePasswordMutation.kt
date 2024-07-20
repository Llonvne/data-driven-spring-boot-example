package cn.llonvne.example.db.user.mutation

/**
 * Represent a mutation that update a user password.
 */
data class DbUserUpdatePasswordMutation(
    val userId: String,
    val oldPassword: String,
    val newPassword: String
)