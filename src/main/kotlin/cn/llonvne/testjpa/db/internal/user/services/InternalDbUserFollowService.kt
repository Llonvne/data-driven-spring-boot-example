package cn.llonvne.testjpa.db.internal.user.services

import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollow
import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollowIdType
import cn.llonvne.testjpa.db.internal.user.repo.DbUserFollowTableRepository
import org.springframework.stereotype.Service

@Service
data class InternalDbUserFollowService(
    val repository: DbUserFollowTableRepository
) {
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