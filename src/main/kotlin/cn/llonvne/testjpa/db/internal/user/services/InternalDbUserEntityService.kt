package cn.llonvne.testjpa.db.internal.user.services

import cn.llonvne.testjpa.db.internal.user.UserInternalApi
import cn.llonvne.testjpa.db.internal.user.entity.DbUserEntity
import cn.llonvne.testjpa.db.internal.user.repo.DbUserEntityRepository
import org.springframework.stereotype.Service

/**
 * Service class for DbUserEntity.
 */
@Service
@UserInternalApi
class InternalDbUserEntityService(private val dbUserEntityRepository: DbUserEntityRepository) {
    @OptIn(UserInternalApi::class)
    fun getUserEntityByIdRaw(userId: String): DbUserEntity? {
        return kotlin.runCatching {
            dbUserEntityRepository.getReferenceById(userId)
        }.getOrNull()
    }

    fun isUserIdExist(userId: String): Boolean {
        return dbUserEntityRepository.existsById(userId)
    }
}