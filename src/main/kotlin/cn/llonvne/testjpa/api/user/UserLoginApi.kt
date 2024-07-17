package cn.llonvne.testjpa.api.user

import cn.llonvne.testjpa.req2resp.UserLoginRequest
import cn.llonvne.testjpa.service.internal.response.toResponseEntity
import cn.llonvne.testjpa.service.user.UserAuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/login")
class UserLoginApi(
    private val userAuthenticationService: UserAuthenticationService,
) {
    @PostMapping
    fun login(@RequestBody userLoginRequest: UserLoginRequest): ResponseEntity<*> {
        return userAuthenticationService.login(userLoginRequest).toResponseEntity()
    }
}