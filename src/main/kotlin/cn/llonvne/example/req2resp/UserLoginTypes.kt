package cn.llonvne.example.req2resp

import cn.llonvne.example.db.token.type.UserToken

data class UserLoginRequest(val username: String, val password: String)

data class UserLoginResponse(val token: UserToken)

data class UserResetPasswordRequest(val oldPassword: String, val newPassword: String)