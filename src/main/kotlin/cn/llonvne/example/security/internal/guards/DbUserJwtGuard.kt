package cn.llonvne.example.security.internal.guards

import cn.llonvne.example.db.token.service.UserTokenService
import cn.llonvne.example.db.user.DbUserService
import cn.llonvne.example.db.user.pub.DbUser
import cn.llonvne.example.jwt.Jwt
import cn.llonvne.example.req2resp.validate.validate
import cn.llonvne.example.security.Guard
import cn.llonvne.example.security.Guard.Companion.GuardResult
import cn.llonvne.example.security.GuardType
import cn.llonvne.example.security.internal.SecurityInternalApi
import cn.llonvne.example.service.user.UserAuthenticationService
import cn.llonvne.example.tools.storeNotNull
import com.auth0.jwt.exceptions.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import kotlin.reflect.KClass

@SecurityInternalApi
@Component
class DbUserJwtGuard(
    private val jwt: Jwt<DbUser>,
    private val userTokenService: UserTokenService,
    private val userService: UserAuthenticationService
) : Guard<DbUser> {

    override val name = GuardType.UserToken

    override val pKClass: KClass<*> = DbUser::class

    override fun pass(e: HttpServletRequest): GuardResult<DbUser> {
        val token = tokenRaw(e) ?: return GuardResult.Failed("TokenRaw Invalid", HttpStatus.UNAUTHORIZED)

        val dbUser = try {
            jwt.decode(token)
        } catch (e: AlgorithmMismatchException) {
            return GuardResult.Failed("TokenAlgorithmMismatch", HttpStatus.UNAUTHORIZED)
        } catch (e: SignatureVerificationException) {
            return GuardResult.Failed("TokenSignatureVerificationException", HttpStatus.UNAUTHORIZED)
        } catch (e: TokenExpiredException) {
            return GuardResult.Failed("JwtTokenExpired", HttpStatus.UNAUTHORIZED)
        } catch (e: MissingClaimException) {
            return GuardResult.Failed("MissingClaimException", HttpStatus.BAD_REQUEST)
        } catch (e: IncorrectClaimException) {
            return GuardResult.Failed("IncorrectClaimException", HttpStatus.BAD_REQUEST)
        }

        if (!userTokenService.validate(token)) {
            return GuardResult.Failed("Token invalid", HttpStatus.UNAUTHORIZED)
        }

        userService

        storeNotNull(dbUser)

        return GuardResult.Pass(dbUser)
    }

    override fun tokenRaw(e: HttpServletRequest): String? {
        return e.getHeader(HttpHeaders.AUTHORIZATION)
    }
}