package cn.llonvne.testjpa.jwt

import kotlin.time.Duration
import kotlin.time.Duration.Companion.days

interface Jwt<E> {
    fun generate(value: E, expiration: Duration = 30.days): String

    fun decode(token: String): E
}