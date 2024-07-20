package cn.llonvne.example.security

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import kotlin.reflect.KClass

/**
 * Guard to extract credential [P] from [HttpServletRequest]
 */
interface Guard<P : GuardTypeMarker> {
    val name: GuardType

    val pKClass: KClass<*>

    fun pass(e: HttpServletRequest): GuardResult<P>

    fun tokenRaw(e: HttpServletRequest): String?

    companion object {
        sealed interface GuardResult<P> {
            data class Pass<P>(val p: P, val message: String = "") : GuardResult<P>

            data class Failed<P>(val message: String,val status: HttpStatus) : GuardResult<P>
        }
    }
}