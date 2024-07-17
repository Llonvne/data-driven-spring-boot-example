package cn.llonvne.testjpa.req2resp

data class UserLoginRequest(val username: String, val password: String)

data class UserLoginResponse(val token: String)