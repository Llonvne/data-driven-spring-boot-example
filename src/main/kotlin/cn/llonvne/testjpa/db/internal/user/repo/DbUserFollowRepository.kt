package cn.llonvne.testjpa.db.internal.user.repo

import cn.llonvne.testjpa.db.internal.user.UserInternalApi
import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollow
import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollowIdType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for the [cn.llonvne.testjpa.db.internal.user.entity.DbUserFollow]
 */
@Repository
@UserInternalApi
interface DbUserFollowRepository : JpaRepository<DbUserFollow, DbUserFollowIdType> {
    fun findByUserId(userId: String): List<DbUserFollow>
}