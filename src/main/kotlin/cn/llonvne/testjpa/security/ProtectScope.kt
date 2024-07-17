package cn.llonvne.testjpa.security

import cn.llonvne.testjpa.tools.currentRequest
import cn.llonvne.testjpa.tools.requestWebApplication
import org.springframework.beans.factory.getBeansOfType
import org.springframework.stereotype.Component

interface ProtectScope<P : GuardTypeMarker> {
    @Component
    companion object {

        sealed interface GuardResult<P> {
            data class Failed<P>(val message: String = "") : GuardResult<P>

            data class Passed<P>(val p: P) : GuardResult<P>
        }

        fun <P> GuardResult<P>.unwrap(): P {
            return (this as GuardResult.Passed<P>).p
        }

        inline fun <reified P : GuardTypeMarker, R> protect(protectScope: ProtectScope<P>.(P) -> R): GuardResult<R> {
            val guard = requestWebApplication().getBeansOfType<Guard<*>>().values.firstOrNull {
                it.pKClass == P::class
            }

            if (guard == null) {
                return GuardResult.Failed("guard not found")
            } else {
                val p = guard.pass(currentRequest()) as P? ?: return GuardResult.Failed("guard not passed")
                return GuardResult.Passed(object : ProtectScope<P> {}.protectScope(p))
            }
        }

        inline fun <reified P : GuardTypeMarker> protect(): GuardResult<P> {
            return protect<P, P> { it }
        }
    }
}



