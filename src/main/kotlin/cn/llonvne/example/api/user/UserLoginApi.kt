package cn.llonvne.example.api.user

import cn.llonvne.example.req2resp.UserLoginRequest
import cn.llonvne.example.req2resp.UserResetPasswordRequest
import cn.llonvne.example.req2resp.validate.ValidatableRequest
import cn.llonvne.example.req2resp.validate.ofUUID
import cn.llonvne.example.req2resp.validate.validate
import cn.llonvne.example.security.shorthand.LoginScope
import cn.llonvne.example.service.response.toResponseEntity
import cn.llonvne.example.service.user.UserAuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserLoginApi(
    private val service: UserAuthenticationService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: UserLoginRequest): ResponseEntity<*> {
        return service.login(request).toResponseEntity()
    }

    @PutMapping
    fun refresh(): ResponseEntity<*> = LoginScope.loginOrUnauthorized { user ->
        service.refresh(tokenRaw, user).toResponseEntity()
    }

    @PatchMapping("/reset-password")
    fun resetPassword(@RequestBody request: UserResetPasswordRequest): ResponseEntity<*> =
        LoginScope.loginOrUnauthorized { user ->
            service.resetPassword(tokenRaw, user, request).toResponseEntity()
        }
}