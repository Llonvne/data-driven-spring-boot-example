package cn.llonvne.example.db.internal.token.repo

import cn.llonvne.example.db.internal.token.entity.DbTokenEntity
import cn.llonvne.example.db.token.TokenInternalApi
import org.springframework.data.jpa.repository.JpaRepository

@TokenInternalApi
interface DbTokenEntityRepository : JpaRepository<DbTokenEntity, String> {
    fun findByType(type: String): List<DbTokenEntity>
}