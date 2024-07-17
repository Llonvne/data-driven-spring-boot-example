package cn.llonvne.testjpa.security.shorthand

import cn.llonvne.testjpa.db.user.pub.DbUser
import cn.llonvne.testjpa.req2resp.UnauthorizedResponseEntity
import cn.llonvne.testjpa.security.ProtectScope
import cn.llonvne.testjpa.security.ProtectScope.Companion.GuardResult
import cn.llonvne.testjpa.security.ProtectScope.Companion.protect
import org.springframework.http.ResponseEntity

class LoginScope private constructor() : ProtectScope<DbUser> {
    companion object {
        fun loginOrUnauthorized(
            dsl: LoginScope.(DbUser) -> ResponseEntity<*>
        ): ResponseEntity<*> {
            return when (val user = protect<DbUser>()) {
                is GuardResult.Failed -> UnauthorizedResponseEntity<String>()
                is GuardResult.Passed -> {
                    LoginScope().dsl(user.p)
                }
            }
        }
    }
}