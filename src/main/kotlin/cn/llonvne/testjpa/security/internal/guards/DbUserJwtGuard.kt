package cn.llonvne.testjpa.security.internal.guards

import cn.llonvne.testjpa.db.user.pub.DbUser
import cn.llonvne.testjpa.jwt.Jwt
import cn.llonvne.testjpa.security.Guard
import cn.llonvne.testjpa.security.GuardType
import cn.llonvne.testjpa.tools.storeNotNull
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@Component
class DbUserJwtGuard(
    private val jwt: Jwt<DbUser>
) : Guard<DbUser> {

    override val name = GuardType.UserToken

    override val pKClass: KClass<*> = DbUser::class

    override fun pass(e: HttpServletRequest): DbUser? {
        val token = e.getHeader(HttpHeaders.AUTHORIZATION)

        val dbUser = kotlin.runCatching {
            jwt.decode(token)
        }.getOrNull()

        storeNotNull(dbUser)

        return dbUser
    }
}