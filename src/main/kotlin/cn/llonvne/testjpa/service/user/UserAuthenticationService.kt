package cn.llonvne.testjpa.service.user

import cn.llonvne.testjpa.db.result.OneQueryTypedResult
import cn.llonvne.testjpa.db.user.DbUserService
import cn.llonvne.testjpa.db.user.pub.DbUser
import cn.llonvne.testjpa.db.user.query.DbUserLoginQuery
import cn.llonvne.testjpa.jwt.Jwt
import cn.llonvne.testjpa.req2resp.UserLoginRequest
import cn.llonvne.testjpa.req2resp.UserLoginResponse
import cn.llonvne.testjpa.service.internal.response.OneResponse
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userService: DbUserService, private val dbUserJwt: Jwt<DbUser>) {
    fun login(userLoginRequest: UserLoginRequest): OneResponse<UserLoginResponse> {
        return when (val result =
            userService.login(DbUserLoginQuery(userLoginRequest.username, userLoginRequest.password))) {
            is OneQueryTypedResult.None -> OneResponse.None("")
            is OneQueryTypedResult.One -> {
                return OneResponse.One(UserLoginResponse(dbUserJwt.generate(result.value)))
            }
        }
    }
}