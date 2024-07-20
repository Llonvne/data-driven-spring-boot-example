package cn.llonvne.example.req2resp.validate

import cn.llonvne.example.req2resp.validate.ValidatableRequest.Validated
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import kotlin.reflect.KProperty

/**
 * Indicated a Http Request Body need to be validated.
 *
 * it will be processed by [cn.llonvne.example.req2resp.validate.ValidatableRequestAspect]
 *
 * @param R request type
 */
fun interface ValidatableRequest<R> {

    /**
     * validate function implementation by Request
     */
    fun validate(): Validated<R>

    sealed interface Validated<R> {

        val r: R

        data class Pass<R>(override val r: R) : Validated<R>

        data class Reject<R>(override val r: R, val reason: Map<KProperty<*>, String>) : Validated<R>
    }
}

class ValidateRequestDsl<R>(private val r: R) {

    data class PassFunction<E>(val property: KProperty<E>, val failMessage: String)

    val result = mutableSetOf<PassFunction<*>>()

    fun <E> of(property: KProperty<E>, failMessage: String, pass: (E) -> Boolean) {
        val passResult = pass(property.call())
        if (!passResult) {
            result += PassFunction(property, failMessage)
        }
    }

    fun build(): Validated<R> {
        return if (result.isEmpty()) {
            Validated.Pass(r)
        } else {
            Validated.Reject(r, result.associate {
                it.property to it.failMessage
            })
        }
    }
}


fun <R> R.validate(dsl: ValidateRequestDsl<R>.() -> Unit): Validated<R> {
    val dslImpl = ValidateRequestDsl(this)
    dslImpl.dsl()
    return dslImpl.build()
}

@Aspect
@Component
class ValidatableRequestAspect {
    @Around("args(request) && within(cn.llonvne.example.api.*.*)")
    fun beforeMethod(joinPoint: ProceedingJoinPoint, request: ValidatableRequest<*>): Any? {
        return when (val resp = request.validate()) {
            is Validated.Pass -> joinPoint.proceed()
            is Validated.Reject -> ResponseEntity<Map<String, String>>(
                resp.reason.mapKeys { it.key.name }, HttpStatus.BAD_REQUEST
            )
        }
    }
}
