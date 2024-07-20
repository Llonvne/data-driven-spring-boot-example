package cn.llonvne.example.req2resp

import cn.llonvne.example.db.token.type.UserToken
import cn.llonvne.example.req2resp.validate.ValidatableRequest
import cn.llonvne.example.req2resp.validate.validate

data class UserLoginRequest(val username: String, val password: String)

data class UserLoginResponse(val token: UserToken)

data class UserResetPasswordRequest(val oldPassword: String, val newPassword: String) :
    ValidatableRequest<UserResetPasswordRequest> {
    override fun validate(): ValidatableRequest.Validated<UserResetPasswordRequest> = validate {
        of(::newPassword, "The new password cannot be the same as the old password") {
            it != oldPassword
        }
    }
}