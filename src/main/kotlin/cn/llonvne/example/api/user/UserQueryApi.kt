package cn.llonvne.example.api.user

import cn.llonvne.example.db.user.pub.UserId
import cn.llonvne.example.security.shorthand.LoginScope.Companion.loginOrUnauthorized
import cn.llonvne.example.service.response.toResponseEntity
import cn.llonvne.example.service.user.UserQueryService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserQueryApi(
    private val userQueryService: UserQueryService
) {

    @GetMapping
    fun user(): ResponseEntity<*> = loginOrUnauthorized { user ->
        ResponseEntity<UserId>(user, HttpStatus.OK)
    }

    @GetMapping("/username")
    fun username(): ResponseEntity<*> = loginOrUnauthorized { userId ->
        userQueryService.username(userId).toResponseEntity()
    }
}