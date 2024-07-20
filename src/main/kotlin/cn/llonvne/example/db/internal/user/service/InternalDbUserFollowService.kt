package cn.llonvne.example.db.internal.user.service

import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserFollow
import cn.llonvne.example.db.internal.user.entity.DbUserFollowIdType
import cn.llonvne.example.db.internal.user.repo.DbUserFollowRepository
import org.springframework.stereotype.Service

/**
 * Service class for DbUserFollow
 */
@Service
@UserInternalApi
data class InternalDbUserFollowService(
    val repository: DbUserFollowRepository
) {
    @OptIn(UserInternalApi::class)
    fun isFollow(followerId: String, followeeId: String): Boolean {
        return repository.existsById(DbUserFollowIdType(userId = followerId, followeeId = followeeId))
    }

    fun follow(followerId: String, followeeId: String): DbUserFollow {
        return repository.save(DbUserFollow(userId = followerId, followeeId = followeeId))
    }

    fun unfollow(followerId: String, followeeId: String) {
        return repository.delete(DbUserFollow(userId = followerId, followeeId = followeeId))
    }
}