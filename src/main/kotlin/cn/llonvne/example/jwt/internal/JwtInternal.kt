package cn.llonvne.example.jwt.internal

import cn.llonvne.example.jwt.JwtInternalApi
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm


@JwtInternalApi
class JwtInternal(secret: String) {
    val algorithm: Algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).build()
}
