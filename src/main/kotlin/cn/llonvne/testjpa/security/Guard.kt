package cn.llonvne.testjpa.security

import jakarta.servlet.http.HttpServletRequest
import kotlin.reflect.KClass

interface Guard<P : GuardTypeMarker> {
    val name: GuardType

    val pKClass: KClass<*>

    fun pass(e: HttpServletRequest): P?
}