package cn.llonvne.example.api.user

import cn.llonvne.example.req2resp.UsernameTraceResponse
import cn.llonvne.example.security.shorthand.LoginScope.Companion.loginOrUnauthorized
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserQueryApi {
    @GetMapping("/username")
    fun username(): ResponseEntity<*> = loginOrUnauthorized { user ->
        ResponseEntity(
            UsernameTraceResponse(username = user.username),
            HttpStatus.OK
        )
    }
}