package cn.llonvne.example.db.internal.user.repo

import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserFollow
import cn.llonvne.example.db.internal.user.entity.DbUserFollowIdType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for the [cn.llonvne.example.db.internal.user.entity.DbUserFollow]
 */
@Repository
@UserInternalApi
interface DbUserFollowRepository : JpaRepository<DbUserFollow, DbUserFollowIdType> {
    fun findByUserId(userId: String): List<DbUserFollow>

    fun findByFolloweeId(followeeId: String): List<DbUserFollow>
}