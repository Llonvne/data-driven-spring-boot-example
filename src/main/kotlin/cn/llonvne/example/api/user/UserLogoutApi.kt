package cn.llonvne.example.api.user

import cn.llonvne.example.req2resp.abc.TestRequest
import cn.llonvne.example.security.shorthand.LoginScope
import cn.llonvne.example.service.response.toResponseEntity
import cn.llonvne.example.service.user.UserAuthenticationService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserLogoutApi(
    private val userAuthenticationService: UserAuthenticationService
) {

    @PostMapping("/logout")
    fun logout() = LoginScope.loginOrUnauthorized {
        userAuthenticationService.expire(tokenRaw).toResponseEntity()
    }

    @PostMapping("/testa")
    fun test(@RequestBody request: TestRequest): ResponseEntity<*> {
        return ResponseEntity<String>("1", HttpStatus.OK)
    }
}