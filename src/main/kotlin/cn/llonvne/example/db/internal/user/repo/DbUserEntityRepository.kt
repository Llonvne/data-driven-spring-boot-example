package cn.llonvne.example.db.internal.user.repo

import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * Repository for the [cn.llonvne.example.db.internal.user.entity.DbUserEntity]
 */
@Repository
@UserInternalApi
interface DbUserEntityRepository : JpaRepository<DbUserEntity, String> {
    fun findByUsername(username: String): DbUserEntity?
}