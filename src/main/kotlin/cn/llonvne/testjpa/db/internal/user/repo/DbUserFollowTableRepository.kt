package cn.llonvne.testjpa.db.internal.user.repo

import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollow
import cn.llonvne.testjpa.db.internal.user.entity.DbUserFollowIdType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DbUserFollowTableRepository : JpaRepository<DbUserFollow, DbUserFollowIdType> {
    fun findByUserId(userId: String): List<DbUserFollow>
}