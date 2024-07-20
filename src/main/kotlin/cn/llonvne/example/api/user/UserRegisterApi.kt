package cn.llonvne.example.api.user

import cn.llonvne.example.req2resp.UserRegisterRequest
import cn.llonvne.example.service.response.toResponseEntity
import cn.llonvne.example.service.user.UserAuthenticationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserRegisterApi(
    private val userService: UserAuthenticationService
) {
    @PostMapping("/register")
    fun register(@RequestBody request: UserRegisterRequest): ResponseEntity<*> {
        return userService.register(request).toResponseEntity()
    }
}