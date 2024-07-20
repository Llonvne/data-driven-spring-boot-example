package cn.llonvne.example.service.user

import cn.llonvne.example.db.user.DbUserService
import cn.llonvne.example.db.user.mutation.DbUserFollowMutation
import cn.llonvne.example.db.user.mutation.DbUserUnfollowMutation
import cn.llonvne.example.db.user.query.DbUserFolloweesQuery
import cn.llonvne.example.db.user.query.DbUserFollowersQuery
import cn.llonvne.example.service.response.OneResponse
import cn.llonvne.example.service.response.one
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserFollowService(
    private val userService: DbUserService
) {
    fun followees(userId: String): OneResponse<List<String>> {
        return userService.followees(DbUserFolloweesQuery(userId)).one(HttpStatus.NO_CONTENT)
    }

    fun followers(followeeId: String): OneResponse<List<String>> {
        return userService.followers(DbUserFollowersQuery(followeeId)).one(HttpStatus.NO_CONTENT)
    }

    fun follow(followee: String, follower: String): OneResponse<Unit> {
        return userService.follow(DbUserFollowMutation(follower, followee)).one(HttpStatus.BAD_REQUEST)
    }

    fun unfollow(followee: String, follower: String): OneResponse<Unit> {
        return userService.unfollow(DbUserUnfollowMutation(follower, followee)).one(HttpStatus.BAD_REQUEST)
    }
}