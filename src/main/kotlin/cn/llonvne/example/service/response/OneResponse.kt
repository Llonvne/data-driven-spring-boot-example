package cn.llonvne.example.service.response

import cn.llonvne.example.db.result.MutationResult
import cn.llonvne.example.db.result.MutationTypedResult
import cn.llonvne.example.db.result.OneQueryResult
import cn.llonvne.example.db.result.OneQueryTypedResult
import cn.llonvne.example.service.response.OneResponse.None
import cn.llonvne.example.service.response.OneResponse.One
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

/**
 * Unified return type at the Service layer.
 *
 * @property message The returned message, which is usually empty in the One variant and usually a message describing why the function failed in the None variant.
 * @property status HttpStatusCode
 */
sealed interface OneResponse<R> {
    val message: String
    val status: HttpStatusCode

    /**
     * One variant of OneResponse.
     *
     * @property response the [R] type
     */
    data class One<R>(
        val response: R,
        override val message: String = "",
        override val status: HttpStatusCode = HttpStatus.OK
    ) : OneResponse<R>

    data class None<R>(override val message: String, override val status: HttpStatusCode = HttpStatus.BAD_REQUEST) :
        OneResponse<R>

}

@JvmName("mapToR1")
fun <R, R1> OneResponse<R>.map(dsl: (R) -> R1): OneResponse<R1> {
    return when (this) {
        is None -> None(message, status)
        is One -> One(dsl(response), message, status)
    }
}

@JvmName("mapToORR1")
fun <R, R1> OneResponse<R>.mapTo(dsl: (R) -> OneResponse<R1>): OneResponse<R1> {
    return when (this) {
        is None -> None(message, status)
        is One -> dsl(response)
    }
}

fun <R> OneResponse<R>.onSuccess(dsl: (R) -> Unit): OneResponse<R> {
    if (this is One<R>) {
        dsl(response)
    }
    return this
}

/**
 * Convert a [OneResponse] to a [ResponseEntity].
 */
inline fun <reified R> OneResponse<R>.toResponseEntity(): ResponseEntity<*> {
    return when (this) {
        is None -> {
            ResponseEntity<String>(message, status)
        }

        is One -> {
            ResponseEntity<R>(response, status)
        }
    }
}

/**
 * Convert [OneQueryResult] to [OneResponse]
 */
fun <E : Any> OneQueryResult<E>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is OneQueryResult.None -> None(status = noneStatus, message = this.message)
        is OneQueryResult.One -> One(status = okStatus, message = this.message, response = this.value)
    }
}

/**
 * Convert [OneQueryTypedResult] to [OneResponse]
 */
fun <E : Any, ERR : Enum<*>> OneQueryTypedResult<E, ERR>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is OneQueryTypedResult.None -> None(status = noneStatus, message = err.toString())
        is OneQueryTypedResult.One -> One(status = okStatus, message = this.message, response = this.value)
    }
}

/**
 * Convert [MutationResult] to [OneResponse]
 */
fun <E : Any> MutationResult<E>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is MutationResult.Failed -> None(status = noneStatus, message = message)
        is MutationResult.Success -> One(status = okStatus, message = this.message, response = this.value)
    }
}

/**
 * Convert [MutationTypedResult] to [OneResponse]
 */
fun <E : Any, ERR : Enum<*>> MutationTypedResult<E, ERR>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
    oneMessage: String? = null,
    noneMessage: String? = null
): OneResponse<E> {
    return when (this) {
        is MutationTypedResult.None -> None(status = noneStatus, message = noneMessage ?: err.toString())
        is MutationTypedResult.One -> One(
            status = okStatus,
            message = oneMessage ?: message,
            response = this.value
        )
    }
}