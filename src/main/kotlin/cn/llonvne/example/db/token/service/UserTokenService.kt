package cn.llonvne.example.db.token.service

import cn.llonvne.example.db.result.MTR
import cn.llonvne.example.db.result.MutationTypedResult
import cn.llonvne.example.db.result.OneQueryResult
import cn.llonvne.example.db.result.OneQueryResult.Companion.map
import cn.llonvne.example.db.result.OneQueryTypedResult
import cn.llonvne.example.db.result.OneQueryTypedResult.Companion.mapTo
import cn.llonvne.example.db.result.OneQueryTypedResult.None
import cn.llonvne.example.db.token.TokenInternalApi
import cn.llonvne.example.db.token.error.GetTokenQueryError
import cn.llonvne.example.db.token.error.NewTokenMutationError
import cn.llonvne.example.db.token.error.RemoveTokenMutationError
import cn.llonvne.example.db.token.mutation.GenerateAndSaveTokenMutation
import cn.llonvne.example.db.token.mutation.NewTokenMutation
import cn.llonvne.example.db.token.mutation.RemoveTokenMutation
import cn.llonvne.example.db.token.query.FromTokenRawToUserTokenQuery
import cn.llonvne.example.db.token.query.GetTokenByTypeQuery
import cn.llonvne.example.db.token.query.GetTokenQuery
import cn.llonvne.example.db.token.type.TokenTypeEnum
import cn.llonvne.example.db.token.type.UserToken
import cn.llonvne.example.db.user.pub.UserId
import cn.llonvne.example.jwt.Jwt
import org.springframework.stereotype.Service

@Service
@OptIn(TokenInternalApi::class)
class UserTokenService(
    private val tokenService: TokenService,
    private val jwt: Jwt<UserId>
) {
    fun login(mutation: GenerateAndSaveTokenMutation): MutationTypedResult<UserToken, NewTokenMutationError> {
        val tokenValue = jwt.generate(mutation.userId)
        val userToken = UserToken(tokenValue)
        val result = tokenService.save(NewTokenMutation(userToken))
        return result
    }

    fun fromTokenRaw(query: FromTokenRawToUserTokenQuery): OneQueryTypedResult<UserToken, GetTokenQueryError> {
        return tokenService.getEntity(GetTokenQuery(query.tokenRaw)).mapTo { entity ->
            if (entity.type == TokenTypeEnum.USER.name) {
                OneQueryTypedResult.One(UserToken(entity.token))
            } else {
                None(GetTokenQueryError.TokenTypeMismatch)
            }
        }
    }

    fun remove(userToken: UserToken): MTR<Unit, RemoveTokenMutationError> {
        return tokenService.remove(RemoveTokenMutation(userToken))
    }

    fun validate(tokenRaw: String): Boolean {
        return when (fromTokenRaw(FromTokenRawToUserTokenQuery(tokenRaw))) {
            is None -> false
            is OneQueryTypedResult.One -> true
        }
    }

    fun isLoginServer(userId: UserId): OneQueryResult<UserToken> {
        return tokenService.byType(GetTokenByTypeQuery(TokenTypeEnum.USER))
            .map { userTokens ->
                val user = userTokens.find {
                    jwt.decode(it.token) == userId
                } ?: return@map OneQueryResult.None("User not found in Login Users")
                return@map OneQueryResult.One(UserToken(user.token), "user login")
            }
    }
}