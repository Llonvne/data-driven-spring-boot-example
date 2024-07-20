package cn.llonvne.example.security

import cn.llonvne.example.security.Guard.Companion.GuardResult
import cn.llonvne.example.tools.currentRequest
import cn.llonvne.example.tools.requestWebApplication
import org.springframework.beans.factory.getBeansOfType
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component

interface ProtectScope<P : GuardTypeMarker> {
    @Component
    companion object {

        sealed interface ProtectResult<P> {
            val tokenRaw: String?

            data class Failed<P>(override val tokenRaw: String?, val message: String = "", val status: HttpStatus) :
                ProtectResult<P>

            data class Passed<P>(override val tokenRaw: String, val p: P) : ProtectResult<P>

            data class GuardNotFound<P>(val message: String = "") : ProtectResult<P> {
                override val tokenRaw: String = "<GUARD NOT FOUND>"
            }
        }

        fun <P> ProtectResult<P>.unwrap(): P {
            return (this as ProtectResult.Passed<P>).p
        }

        inline fun <reified P : GuardTypeMarker, R> protect(protectScope: ProtectScope<P>.(P) -> R): ProtectResult<R> {
            val guard = requestWebApplication().getBeansOfType<Guard<*>>().values.firstOrNull {
                it.pKClass == P::class
            }

            if (guard == null) {
                return ProtectResult.GuardNotFound("guard not found")
            } else {
                val req = currentRequest()
                val tokenRaw = guard.tokenRaw(req) ?: return ProtectResult.Failed(
                    null,
                    "TokenRaw is null",
                    HttpStatus.UNAUTHORIZED
                )
                return when (val p = guard.pass(req) as GuardResult<P>) {
                    is GuardResult.Failed -> ProtectResult.Failed(
                        guard.tokenRaw(req),
                        message = p.message,
                        status = p.status
                    )

                    is GuardResult.Pass -> ProtectResult.Passed(
                        tokenRaw,
                        object : ProtectScope<P> {}.protectScope(p.p)
                    )
                }
            }
        }

        inline fun <reified P : GuardTypeMarker> protect(): ProtectResult<P> {
            return protect<P, P> { it }
        }
    }
}



