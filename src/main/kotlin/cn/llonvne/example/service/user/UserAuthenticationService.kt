package cn.llonvne.example.service.user

import cn.llonvne.example.db.result.OneQueryTypedResult.Companion.map
import cn.llonvne.example.db.result.map
import cn.llonvne.example.db.token.mutation.GenerateAndSaveTokenMutation
import cn.llonvne.example.db.token.query.FromTokenRawToUserTokenQuery
import cn.llonvne.example.db.token.service.UserTokenService
import cn.llonvne.example.db.token.type.UserToken
import cn.llonvne.example.db.user.DbUserService
import cn.llonvne.example.db.user.mutation.DbUserNewMutation
import cn.llonvne.example.db.user.mutation.DbUserUpdatePasswordMutation
import cn.llonvne.example.db.user.pub.UserId
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
        return userService.login(DbUserLoginQuery(request.username, request.password))
            .one(HttpStatus.UNAUTHORIZED)
            .mapTo { user ->
                if (tokenService.isLoginServer(user).isOne()) {
                    OneResponse.None(
                        "User already login;\nif you want refresh your token send PUT method with your credential.",
                        status = HttpStatus.CONFLICT
                    )
                } else {
                    tokenService.login(GenerateAndSaveTokenMutation(user))
                        .map { UserLoginResponse(it) }
                        .one(noneStatus = HttpStatus.CONFLICT)
                }
            }
    }

    fun register(request: UserRegisterRequest): OneResponse<UserRegisterResponse> {
        return userService.newUser(DbUserNewMutation(request.username, request.password))
            .one(HttpStatus.BAD_REQUEST)
            .mapTo { newUser ->
                tokenService
                    .login(GenerateAndSaveTokenMutation(newUser))
                    .map { UserRegisterResponse(it) }.one(HttpStatus.CONFLICT)
            }
    }

    fun refresh(token: String, userId: UserId): OneResponse<UserToken> {
        return expire(token).mapTo {
            tokenService.login(GenerateAndSaveTokenMutation(userId)).one(HttpStatus.UNAUTHORIZED)
        }
    }

    fun expire(token: String): OneResponse<String> {
        return tokenService.fromTokenRaw(FromTokenRawToUserTokenQuery(token))
            .map { t ->
                tokenService.remove(t)
                "Logout successful!"
            }
            .one(noneStatus = HttpStatus.UNAUTHORIZED, oneMessage = "Logout successful!", noneMessage = "Invalid token")
    }

    fun resetPassword(
        tokenRaw: String,
        userId: UserId,
        request: UserResetPasswordRequest
    ): OneResponse<String> {
        return userService.updatePassword(
            DbUserUpdatePasswordMutation(
                userId.id,
                request.oldPassword,
                request.newPassword
            )
        )
            .one(noneStatus = HttpStatus.UNAUTHORIZED)
            .onSuccess { expire(tokenRaw) }
            .map { "Updated successful\nYour token has expired.\nPlease login to get a new token" }
    }
}