package cn.llonvne.testjpa.api.user

import cn.llonvne.testjpa.req2resp.UserFollowRequest
import cn.llonvne.testjpa.security.shorthand.LoginScope.Companion.loginOrUnauthorized
import cn.llonvne.testjpa.service.response.toResponseEntity
import cn.llonvne.testjpa.service.user.UserFollowService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserFollowApi(
    val service: UserFollowService
) {
    @GetMapping("/followees")
    fun followees(): ResponseEntity<*> = loginOrUnauthorized { user ->
        service.followees(user.id).toResponseEntity()
    }

    @PostMapping("/follow")
    fun follow(@RequestBody request: UserFollowRequest): ResponseEntity<*> =
        loginOrUnauthorized { user ->
            service.follow(followee = user.id, follower = request.followerId)
                .toResponseEntity()
        }
}