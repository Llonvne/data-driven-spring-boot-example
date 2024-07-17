package cn.llonvne.testjpa.jwt.internal

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

class JwtInternal(secret: String, private val expiration: Duration = 30.days) {
    val algorithm: Algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).build()
}
