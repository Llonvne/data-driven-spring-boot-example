package cn.llonvne.testjpa.db.user

import cn.llonvne.testjpa.db.internal.EntityConverter
import cn.llonvne.testjpa.db.internal.PasswordEncoder
import cn.llonvne.testjpa.db.internal.user.entity.DbUserEntity
import cn.llonvne.testjpa.db.internal.user.repo.DbUserEntityRepository
import cn.llonvne.testjpa.db.internal.user.repo.DbUserFollowTableRepository
import cn.llonvne.testjpa.db.internal.user.services.InternalDbUserEntityService
import cn.llonvne.testjpa.db.internal.user.services.InternalDbUserFollowService
import cn.llonvne.testjpa.db.result.MutationResult
import cn.llonvne.testjpa.db.result.MutationTypedResult
import cn.llonvne.testjpa.db.result.OneQueryResult
import cn.llonvne.testjpa.db.result.OneQueryTypedResult
import cn.llonvne.testjpa.db.user.mutation.*
import cn.llonvne.testjpa.db.user.query.DbUserFolloweesQuery
import cn.llonvne.testjpa.db.user.query.DbUserGetByIdQuery
import cn.llonvne.testjpa.db.user.query.DbUserLoginQuery
import cn.llonvne.testjpa.db.user.type.*
import org.springframework.stereotype.Service

@Service
class DbUserService(
    private val repository: DbUserEntityRepository,
    private val dbUserConverter: EntityConverter<DbUserEntity, DbUser>,
    private val entityService: InternalDbUserEntityService,
    private val followRepo: DbUserFollowTableRepository,
    private val followService: InternalDbUserFollowService
) {
    fun newUser(newUser: DbUserNewMutation): MutationResult<DbUser> {
        if (repository.findByUsername(newUser.username) != null) {
            return MutationResult.Failed("username already exist.")
        }

        val dbUserEntity =
            DbUserEntity(username = newUser.username, password = PasswordEncoder.encode(newUser.password))

        val userEntity: DbUserEntity = repository.save(dbUserEntity)

        val user = dbUserConverter.convert(userEntity)

        return MutationResult.Success(user, "successful create user")
    }

    fun login(loginQuery: DbUserLoginQuery): OneQueryTypedResult<DbUser, DbUserLoginQueryError> {
        val userEntity = repository.findByUsername(loginQuery.username)
            ?: return OneQueryTypedResult.None(DbUserLoginQueryError.UsernameNotExist)

        if (PasswordEncoder.matches(rawPassword = loginQuery.password, encodedPassword = userEntity.password)) {

            val user = dbUserConverter.convert(userEntity)

            return OneQueryTypedResult.One(user, "successful login")
        } else {
            return OneQueryTypedResult.None(DbUserLoginQueryError.PasswordNotCorrect)
        }
    }

    fun getById(getByIdQuery: DbUserGetByIdQuery): OneQueryResult<DbUser> {
        val userEntity =
            entityService.getUserEntityByIdRaw(getByIdQuery.userId) ?: return OneQueryResult.None("User id not exist")

        return OneQueryResult.One(dbUserConverter.convert(userEntity))
    }

    fun update(dbUserUpdateMutation: DbUserUpdateMutation): MutationTypedResult<DbUser, DbUserUpdateMutationError> {
        val userEntity = entityService.getUserEntityByIdRaw(dbUserUpdateMutation.dbUser.id)
            ?: return MutationTypedResult.None(DbUserUpdateMutationError.IdNotExist)

        val updatedUser = userEntity.copy(username = dbUserUpdateMutation.dbUser.username)

        val result = repository.save(updatedUser)

        val dbUser = dbUserConverter.convert(result)

        return MutationTypedResult.One(dbUser)
    }

    fun updatePassword(mutation: DbUserUpdatePasswordMutation): MutationTypedResult<Unit, DbUserUpdatePasswordMutationError> {
        val userEntity = entityService.getUserEntityByIdRaw(mutation.userId)
            ?: return MutationTypedResult.None(DbUserUpdatePasswordMutationError.IdNotExist)

        if (PasswordEncoder.matches(mutation.oldPassword, userEntity.password)) {
            val updatedUserEntity = userEntity.copy(password = PasswordEncoder.encode(mutation.newPassword))

            repository.save(updatedUserEntity)

            return MutationTypedResult.One(Unit)
        } else {
            return MutationTypedResult.None(DbUserUpdatePasswordMutationError.OldPasswordNotCorrect)
        }
    }

    fun follow(mutation: DbUserFollowMutation): MutationTypedResult<Unit, DbUserFollowMutationError> {
        if (!entityService.isUserIdExist(mutation.follower)) {
            return MutationTypedResult.None(DbUserFollowMutationError.FollowerIdNotExist)
        }
        if (!entityService.isUserIdExist(mutation.followee)) {
            return MutationTypedResult.None(DbUserFollowMutationError.FolloweeIdNotExist)
        }
        if (followService.isFollow(followerId = mutation.follower, followeeId = mutation.followee)) {
            return MutationTypedResult.None(DbUserFollowMutationError.AlreadyFollowed)
        }

        followService.follow(followerId = mutation.follower, followeeId = mutation.followee)

        return MutationTypedResult.One(Unit)
    }

    fun unfollow(mutation: DbUserUnfollowMutation): MutationTypedResult<Unit, DbUserUnfollowMutationError> {
        if (!entityService.isUserIdExist(mutation.follower)) {
            return MutationTypedResult.None(DbUserUnfollowMutationError.FollowerIdNotExist)
        }
        if (!entityService.isUserIdExist(mutation.followee)) {
            return MutationTypedResult.None(DbUserUnfollowMutationError.FolloweeIdNotExist)
        }
        if (!followService.isFollow(followerId = mutation.follower, followeeId = mutation.followee)) {
            return MutationTypedResult.None(DbUserUnfollowMutationError.NotFollowed)
        }
        return MutationTypedResult.One(
            followService.unfollow(
                followerId = mutation.follower,
                followeeId = mutation.followee
            )
        )
    }

    fun followees(query: DbUserFolloweesQuery): OneQueryTypedResult<List<String>, DbUserFolloweesQueryError> {
        if (!entityService.isUserIdExist(query.userId)) {
            return OneQueryTypedResult.None(DbUserFolloweesQueryError.UserNotExist)
        }
        return OneQueryTypedResult.One(
            followRepo.findByUserId(query.userId).map {
                it.followeeId
            }
        )
    }

}