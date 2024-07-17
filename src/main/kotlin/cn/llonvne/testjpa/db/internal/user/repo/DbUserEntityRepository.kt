package cn.llonvne.testjpa.db.internal.repo

import cn.llonvne.testjpa.db.internal.entity.DbUserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * 服务于 [cn.llonvne.testjpa.db.internal.entity.DbUserEntity] 的 Repository
 */
@Repository
interface DbUserEntityRepository : JpaRepository<DbUserEntity, String> {
    fun findByUsername(username: String): DbUserEntity?
}