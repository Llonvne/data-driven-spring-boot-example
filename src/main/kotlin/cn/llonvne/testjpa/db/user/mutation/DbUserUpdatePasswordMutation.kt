package cn.llonvne.testjpa.db.user.mutation

data class DbUserUpdatePasswordMutation(
    val userId: String,
    val oldPassword: String,
    val newPassword: String
)