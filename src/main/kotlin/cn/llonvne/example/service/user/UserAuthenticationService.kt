package cn.llonvne.example.service.user

import cn.llonvne.example.db.result.MutationResult
import cn.llonvne.example.db.result.OneQueryTypedResult
import cn.llonvne.example.db.result.map
import cn.llonvne.example.db.token.mutation.GenerateAndSaveTokenMutation
import cn.llonvne.example.db.token.query.FromTokenRawToUserTokenQuery
import cn.llonvne.example.db.token.service.UserTokenService
import cn.llonvne.example.db.token.type.UserToken
import cn.llonvne.example.db.user.DbUserService
import cn.llonvne.example.db.user.mutation.DbUserNewMutation
import cn.llonvne.example.db.user.mutation.DbUserUpdatePasswordMutation
import cn.llonvne.example.db.user.pub.DbUser
import cn.llonvne.example.db.user.query.DbUserLoginQuery
import cn.llonvne.example.req2resp.*
import cn.llonvne.example.service.response.*
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(
    private val userService: DbUserService,
    private val tokenService: UserTokenService
) {
    fun login(request: UserLoginRequest): OneResponse<UserLoginResponse> {
        when (val result =
            userService.login(DbUserLoginQuery(username = request.username, password = request.password))) {
            is OneQueryTypedResult.None -> {
                return OneResponse.None(result.err.name, HttpStatus.UNAUTHORIZED)
            }

            is OneQueryTypedResult.One -> {
                if (tokenService.isLoginServer(result.value).isOne()) {
                    return OneResponse.None(
                        "User already login;\nif you want refresh your token send PUT method with your credential.",
                        status = HttpStatus.CONFLICT
                    )
                }
                return tokenService.login(GenerateAndSaveTokenMutation(result.value))
                    .map { UserLoginResponse(it) }
                    .one(noneStatus = HttpStatus.CONFLICT)
            }
        }
    }

    fun register(request: UserRegisterRequest): OneResponse<UserRegisterResponse> {
        when (val newUser = userService.newUser(DbUserNewMutation(request.username, request.password))) {
            is MutationResult.Failed -> return OneResponse.None(newUser.message, HttpStatus.BAD_REQUEST)
            is MutationResult.Success -> {
                val token = tokenService.login(GenerateAndSaveTokenMutation(newUser.value))
                return token.map { UserRegisterResponse(it) }.one(HttpStatus.CONFLICT)
            }
        }
    }

    fun refresh(token: String, dbUser: DbUser): OneResponse<UserToken> {
        return expire(token).mapTo {
            tokenService.login(GenerateAndSaveTokenMutation(dbUser)).one(HttpStatus.UNAUTHORIZED)
        }
    }

    fun expire(token: String): OneResponse<String> {
        when (val userToken = tokenService.fromTokenRaw(FromTokenRawToUserTokenQuery(token))) {
            is OneQueryTypedResult.None -> {
                return OneResponse.None("Invalid token", HttpStatus.UNAUTHORIZED)
            }

            is OneQueryTypedResult.One -> {
                tokenService.remove(userToken.value)
                return OneResponse.One("Logout successful!")
            }
        }
    }

    fun resetPassword(
        tokenRaw: String,
        dbUser: DbUser,
        resetPasswordRequest: UserResetPasswordRequest
    ): OneResponse<String> {
        return userService.updatePassword(
            DbUserUpdatePasswordMutation(
                dbUser.id,
                resetPasswordRequest.oldPassword,
                resetPasswordRequest.newPassword
            )
        ).one(
            noneStatus = HttpStatus.UNAUTHORIZED,
        )
            .onSuccess {
                expire(tokenRaw)
            }
            .map {
                "Updated successful\nYour token has expired.\nPlease login to get a new token"
            }
    }
}