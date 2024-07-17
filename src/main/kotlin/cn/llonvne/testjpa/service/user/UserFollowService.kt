package cn.llonvne.testjpa.service.user

import cn.llonvne.testjpa.db.user.DbUserService
import cn.llonvne.testjpa.db.user.mutation.DbUserFollowMutation
import cn.llonvne.testjpa.db.user.query.DbUserFolloweesQuery
import cn.llonvne.testjpa.service.response.OneResponse
import cn.llonvne.testjpa.service.response.one
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserFollowService(
    private val userService: DbUserService
) {
    fun followees(userId: String): OneResponse<List<String>> {
        return userService.followees(DbUserFolloweesQuery(userId)).one(HttpStatus.NO_CONTENT)
    }

    fun follow(followee: String, follower: String): OneResponse<Unit> {
        return userService.follow(DbUserFollowMutation(follower, followee)).one(HttpStatus.BAD_REQUEST)
    }
}