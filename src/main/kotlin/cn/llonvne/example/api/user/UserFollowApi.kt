package cn.llonvne.example.api.user

import cn.llonvne.example.req2resp.UserFollowRequest
import cn.llonvne.example.req2resp.UserUnfollowRequest
import cn.llonvne.example.security.shorthand.LoginScope.Companion.loginOrUnauthorized
import cn.llonvne.example.service.response.toResponseEntity
import cn.llonvne.example.service.user.UserFollowService
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

    @GetMapping("/followers")
    fun followers():ResponseEntity<*> = loginOrUnauthorized { user->
        service.followers(user.id).toResponseEntity()
    }

    @PostMapping("/follow")
    fun follow(@RequestBody request: UserFollowRequest): ResponseEntity<*> =
        loginOrUnauthorized { user ->
            service.follow(followee = user.id, follower = request.followerId)
                .toResponseEntity()
        }

    @DeleteMapping("/follow")
    fun unfollow(@RequestBody request: UserUnfollowRequest): ResponseEntity<*> =
        loginOrUnauthorized { user ->
            service.unfollow(followee = user.id, follower = request.followerId).toResponseEntity()
        }
}