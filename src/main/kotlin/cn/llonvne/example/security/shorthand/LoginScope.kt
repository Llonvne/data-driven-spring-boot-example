package cn.llonvne.example.security.shorthand

import cn.llonvne.example.db.user.pub.DbUser
import cn.llonvne.example.req2resp.UnauthorizedResponseEntity
import cn.llonvne.example.security.ProtectScope
import cn.llonvne.example.security.ProtectScope.Companion.ProtectResult
import cn.llonvne.example.security.ProtectScope.Companion.protect
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

/**
 * Shorthand for ProtectScope of DbUser Guard
 */
class LoginScope private constructor(val tokenRaw: String) : ProtectScope<DbUser> {
    companion object {
        fun loginOrUnauthorized(
            dsl: LoginScope.(DbUser) -> ResponseEntity<*>
        ): ResponseEntity<*> {
            return when (val user = protect<DbUser>()) {
                is ProtectResult.Failed -> UnauthorizedResponseEntity<String>()
                is ProtectResult.Passed -> {
                    LoginScope(user.tokenRaw).dsl(user.p)
                }

                is ProtectResult.GuardNotFound -> ResponseEntity<String>(
                    "Guard not found",
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
            }
        }
    }
}