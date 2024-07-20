package cn.llonvne.example.req2resp

import cn.llonvne.example.db.token.type.UserToken
import cn.llonvne.example.req2resp.validate.ValidatableRequest
import cn.llonvne.example.req2resp.validate.validate

data class UserRegisterRequest(val username: String, val password: String) : ValidatableRequest<UserRegisterRequest> {
    override fun validate(): ValidatableRequest.Validated<UserRegisterRequest> = validate {
        of(::username, "Username length must be greater than or equal 6") {
            it.length >= 6
        }
        of(::password, "Password length must be greater than or equal 6") {
            it.length >= 6
        }
    }
}

data class UserRegisterResponse(val token: UserToken)