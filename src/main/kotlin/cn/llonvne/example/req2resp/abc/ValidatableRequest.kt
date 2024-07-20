package cn.llonvne.example.req2resp.abc

import cn.llonvne.example.req2resp.abc.ValidatableRequest.Validated
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1

fun interface ValidatableRequest<R> {
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

data class TestRequest(val a: Int) : ValidatableRequest<TestRequest> {
    override fun validate(): Validated<TestRequest> = validate {
        of(::a, "a must be positive or zero") {
            it >= 0
        }
    }
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
