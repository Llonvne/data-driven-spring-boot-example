package cn.llonvne.testjpa.db.user

import cn.llonvne.testjpa.db.internal.EntityConverter
import cn.llonvne.testjpa.db.internal.PasswordEncoder
import cn.llonvne.testjpa.db.internal.user.UserInternalApi
import cn.llonvne.testjpa.db.internal.user.entity.DbUserEntity
import cn.llonvne.testjpa.db.internal.user.repo.DbUserEntityRepository
import cn.llonvne.testjpa.db.internal.user.repo.DbUserFollowRepository
import cn.llonvne.testjpa.db.internal.user.services.InternalDbUserEntityService
import cn.llonvne.testjpa.db.internal.user.services.InternalDbUserFollowService
import cn.llonvne.testjpa.db.result.*
import cn.llonvne.testjpa.db.user.mutation.*
import cn.llonvne.testjpa.db.user.pub.DbUser
import cn.llonvne.testjpa.db.user.query.DbUserFolloweesQuery
import cn.llonvne.testjpa.db.user.query.DbUserGetByIdQuery
import cn.llonvne.testjpa.db.user.query.DbUserLoginQuery
import cn.llonvne.testjpa.db.user.type.*
import cn.llonvne.testjpa.db.user.type.DbUserLoginQueryError.PasswordNotCorrect
import cn.llonvne.testjpa.db.user.type.DbUserLoginQueryError.UsernameNotExist
import cn.llonvne.testjpa.db.user.type.DbUserUpdateMutationError.IdNotExist
import cn.llonvne.testjpa.db.user.type.DbUserUpdatePasswordMutationError.OldPasswordNotCorrect
import org.springframework.stereotype.Service

@OptIn(UserInternalApi::class)
@Service
class DbUserService(
    private val repository: DbUserEntityRepository,
    private val dbUserConverter: EntityConverter<DbUserEntity, DbUser>,
    private val entityService: InternalDbUserEntityService,
    private val followRepo: DbUserFollowRepository,
    private val followService: InternalDbUserFollowService
) {
    fun newUser(newUser: DbUserNewMutation): MR<DbUser> {
        if (repository.findByUsername(newUser.username) != null) {
            return MR_N("username already exist.")
        }

        val entity = DbUserEntity(username = newUser.username, password = PasswordEncoder.encode(newUser.password))

        val userEntity = repository.save(entity)

        val user = dbUserConverter.convert(userEntity)

        return MR_O(user, "successful create user")
    }

    fun login(query: DbUserLoginQuery): OQTR<DbUser, DbUserLoginQueryError> {
        val entity = repository.findByUsername(query.username)
            ?: return OQTR_N(UsernameNotExist)

        if (PasswordEncoder.matches(rawPassword = query.password, encodedPassword = entity.password)) {

            val user = dbUserConverter.convert(entity)

            return OQTR_O(user, "successful login")
        } else {
            return OQTR_N(PasswordNotCorrect)
        }
    }

    fun getById(query: DbUserGetByIdQuery): OQR<DbUser> {
        val entity =
            entityService.getUserEntityByIdRaw(query.userId) ?: return OQR_N("User id not exist")

        return OQR_O(dbUserConverter.convert(entity))
    }

    fun update(mutation: DbUserUpdateMutation): MTR<DbUser, DbUserUpdateMutationError> {
        val entity = entityService.getUserEntityByIdRaw(mutation.dbUser.id)
            ?: return MTR_N(IdNotExist)

        val updatedUser = entity.copy(username = mutation.dbUser.username)

        val result = repository.save(updatedUser)

        val dbUser = dbUserConverter.convert(result)

        return MTR_O(dbUser)
    }

    fun updatePassword(mutation: DbUserUpdatePasswordMutation): MTR<Unit, DbUserUpdatePasswordMutationError> {
        val entity = entityService.getUserEntityByIdRaw(mutation.userId)
            ?: return MTR_N(DbUserUpdatePasswordMutationError.IdNotExist)

        if (PasswordEncoder.matches(mutation.oldPassword, entity.password)) {
            val updatedEntity = entity.copy(password = PasswordEncoder.encode(mutation.newPassword))

            repository.save(updatedEntity)

            return MTR_O(Unit)
        } else {
            return MTR_N(OldPasswordNotCorrect)
        }
    }

    fun follow(mutation: DbUserFollowMutation): MTR<Unit, DbUserFollowMutationError> {
        if (!entityService.isUserIdExist(mutation.follower)) {
            return MTR_N(DbUserFollowMutationError.FollowerIdNotExist)
        }
        if (!entityService.isUserIdExist(mutation.followee)) {
            return MTR_N(DbUserFollowMutationError.FolloweeIdNotExist)
        }
        if (followService.isFollow(followerId = mutation.follower, followeeId = mutation.followee)) {
            return MTR_N(DbUserFollowMutationError.AlreadyFollowed)
        }

        followService.follow(followerId = mutation.follower, followeeId = mutation.followee)

        return MTR_O(Unit)
    }

    fun unfollow(mutation: DbUserUnfollowMutation): MTR<Unit, DbUserUnfollowMutationError> {
        if (!entityService.isUserIdExist(mutation.follower)) {
            return MTR_N(DbUserUnfollowMutationError.FollowerIdNotExist)
        }
        if (!entityService.isUserIdExist(mutation.followee)) {
            return MTR_N(DbUserUnfollowMutationError.FolloweeIdNotExist)
        }
        if (!followService.isFollow(followerId = mutation.follower, followeeId = mutation.followee)) {
            return MTR_N(DbUserUnfollowMutationError.NotFollowed)
        }
        return MTR_O(
            followService.unfollow(
                followerId = mutation.follower,
                followeeId = mutation.followee
            )
        )
    }

    fun followees(query: DbUserFolloweesQuery): OneQueryTypedResult<List<String>, DbUserFolloweesQueryError> {
        if (!entityService.isUserIdExist(query.userId)) {
            return OQTR_N(DbUserFolloweesQueryError.UserNotExist)
        }
        return OQTR_O(
            followRepo.findByUserId(query.userId).map {
                it.followeeId
            }
        )
    }

}