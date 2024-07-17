package cn.llonvne.testjpa.service.response

import cn.llonvne.testjpa.db.result.MutationTypedResult
import cn.llonvne.testjpa.db.result.OneQueryResult
import cn.llonvne.testjpa.db.result.OneQueryTypedResult
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity


sealed interface OneResponse<R> {
    data class One<R>(
        val response: R,
        val message: String = "",
        val status: HttpStatusCode = HttpStatus.OK
    ) : OneResponse<R>

    data class None<R>(val message: String, val status: HttpStatusCode = HttpStatus.BAD_REQUEST) : OneResponse<R>
}

inline fun <reified R> OneResponse<R>.toResponseEntity(): ResponseEntity<*> {
    return when (this) {
        is OneResponse.None -> {
            ResponseEntity<String>(message, status)
        }

        is OneResponse.One -> {
            ResponseEntity<R>(response, status)
        }
    }
}

fun <E : Any> E.one(message: String = "", code: HttpStatus = HttpStatus.OK): OneResponse.One<E> {
    return OneResponse.One(this, message, code)
}

fun <E : Any> OneQueryResult<E>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is OneQueryResult.None -> OneResponse.None(status = noneStatus, message = this.message)
        is OneQueryResult.One -> OneResponse.One(status = okStatus, message = this.message, response = this.value)
    }
}

fun <E : Any, ERR : Enum<*>> OneQueryTypedResult<E, ERR>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is OneQueryTypedResult.None -> OneResponse.None(status = noneStatus, message = err.toString())
        is OneQueryTypedResult.One -> OneResponse.One(status = okStatus, message = this.message, response = this.value)
    }
}

fun <E : Any, ERR : Enum<*>> MutationTypedResult<E, ERR>.one(
    noneStatus: HttpStatus,
    okStatus: HttpStatus = HttpStatus.OK,
): OneResponse<E> {
    return when (this) {
        is MutationTypedResult.None -> OneResponse.None(status = noneStatus, message = err.toString())
        is MutationTypedResult.One -> OneResponse.One(status = okStatus, message = this.message, response = this.value)
    }
}