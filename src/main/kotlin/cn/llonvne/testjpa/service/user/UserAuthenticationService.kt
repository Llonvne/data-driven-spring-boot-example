package cn.llonvne.testjpa.service.user

import cn.llonvne.testjpa.db.result.OneQueryTypedResult.Companion.map
import cn.llonvne.testjpa.db.user.DbUserService
import cn.llonvne.testjpa.db.user.pub.DbUser
import cn.llonvne.testjpa.db.user.query.DbUserLoginQuery
import cn.llonvne.testjpa.jwt.Jwt
import cn.llonvne.testjpa.req2resp.UserLoginRequest
import cn.llonvne.testjpa.req2resp.UserLoginResponse
import cn.llonvne.testjpa.service.response.OneResponse
import cn.llonvne.testjpa.service.response.one
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service

@Service
class UserAuthenticationService(private val userService: DbUserService, private val dbUserJwt: Jwt<DbUser>) {
    fun login(userLoginRequest: UserLoginRequest): OneResponse<UserLoginResponse> {
        return userService.login(DbUserLoginQuery(userLoginRequest.username, userLoginRequest.password))
            .map { UserLoginResponse(dbUserJwt.generate(it)) }
            .one(HttpStatus.UNAUTHORIZED)
    }
}