package cn.llonvne.example.db.internal.token.service

import cn.llonvne.example.db.internal.token.entity.DbTokenEntity
import cn.llonvne.example.db.internal.token.repo.DbTokenEntityRepository
import cn.llonvne.example.db.token.TokenInternalApi
import cn.llonvne.example.db.token.type.TokenTypeEnum
import org.springframework.stereotype.Service

@Service
@TokenInternalApi
class InternalDbTokenEntityService(
    private val repository: DbTokenEntityRepository
) {
    fun isTokenRawExist(tokenRaw: String): Boolean {
        return repository.existsById(tokenRaw)
    }

    fun save(entity: DbTokenEntity): DbTokenEntity {
        return repository.save(entity)
    }

    fun getByTokenRawOrNull(tokenValue: String): DbTokenEntity? {
        return try {
            repository.getReferenceById(tokenValue)
        } catch (e: Exception) {
            null
        }
    }

    fun remove(entity: DbTokenEntity) {
        repository.delete(entity)
    }

    fun byType(tokenTypeEnum: TokenTypeEnum): List<DbTokenEntity> {
        return repository.findByType(tokenTypeEnum.name)
    }
}