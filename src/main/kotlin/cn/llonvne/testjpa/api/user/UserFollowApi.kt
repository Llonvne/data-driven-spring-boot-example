package cn.llonvne.testjpa.api.user

import cn.llonvne.testjpa.req2resp.UserFollowRequest
import cn.llonvne.testjpa.security.shorthand.LoginScope
import cn.llonvne.testjpa.service.internal.response.toResponseEntity
import cn.llonvne.testjpa.service.user.UserFollowService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserFollowApi(
    val userFollowService: UserFollowService
) {
    @GetMapping("/followees")
    fun userFollowees(): ResponseEntity<*> = LoginScope.loginOrUnauthorized { loginUser ->
        userFollowService.followees(loginUser.id).toResponseEntity()
    }

    @PostMapping("/follow")
    fun follow(@RequestBody userFollowRequest: UserFollowRequest): ResponseEntity<*> =
        LoginScope.loginOrUnauthorized { loginedUser ->
            userFollowService.follow(followee = loginedUser.id, follower = userFollowRequest.followerId)
                .toResponseEntity()
        }
}