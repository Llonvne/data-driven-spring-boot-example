package cn.llonvne.example.db.internal.user.service

import cn.llonvne.example.db.internal.user.UserInternalApi
import cn.llonvne.example.db.internal.user.entity.DbUserEntity
import cn.llonvne.example.db.internal.user.repo.DbUserEntityRepository
import org.springframework.stereotype.Service

/**
 * Service class for DbUserEntity.
 */
@Service
@UserInternalApi
class InternalDbUserEntityService(private val entityRepo: DbUserEntityRepository) {
    @OptIn(UserInternalApi::class)
    fun getUserEntityByIdRaw(userId: String): DbUserEntity? {
        return kotlin.runCatching {
            entityRepo.getReferenceById(userId)
        }.getOrNull()
    }

    fun isUserIdExist(userId: String): Boolean {
        return entityRepo.existsById(userId)
    }
}