package cn.llonvne.example.req2resp

import cn.llonvne.example.db.token.type.UserToken

data class UserRegisterRequest(val username: String, val password: String)

data class UserRegisterResponse(val token: UserToken)