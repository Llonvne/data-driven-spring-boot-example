package cn.llonvne.example.jwt.user

import cn.llonvne.example.db.user.pub.DbUser
import cn.llonvne.example.jwt.Jwt
import cn.llonvne.example.jwt.JwtInternalApi
import cn.llonvne.example.jwt.internal.JwtInternal
import com.auth0.jwt.JWT
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.stereotype.Component
import java.time.Instant
import kotlin.time.Duration
import kotlin.time.toJavaDuration

@JwtInternalApi
@Component
private class DbUserJwt(private val jwtInternal: JwtInternal, private val objectMapper: ObjectMapper) : Jwt<DbUser> {
    override fun generate(value: DbUser, expiration: Duration): String =
        JWT.create()
            .withSubject(
                objectMapper.writeValueAsString(value)
            )
            .withExpiresAt(Instant.now() + expiration.toJavaDuration())
            .sign(jwtInternal.algorithm)


    override fun decode(token: String): DbUser {
        return objectMapper.readValue(jwtInternal.verifier.verify(token).subject)
    }
}