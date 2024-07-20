package cn.llonvne.example.db.result

import cn.llonvne.example.db.internal.user.DisgustingQueryMutationResultShorthandApi
import cn.llonvne.example.db.result.MutationTypedResult.None
import cn.llonvne.example.db.result.MutationTypedResult.One

@DisgustingQueryMutationResultShorthandApi
typealias MTR<E, ERR> = MutationTypedResult<E, ERR>
@DisgustingQueryMutationResultShorthandApi
typealias MTR_O<E, ERR> = MutationTypedResult.One<E, ERR>
@DisgustingQueryMutationResultShorthandApi
typealias MTR_N<E, ERR> = MutationTypedResult.None<E, ERR>


/**
 * Represent a result of a mutation with a specific error type.
 */
sealed interface MutationTypedResult<R, ERR> {
    data class One<R, ERR>(val value: R, val message: String = "") : MutationTypedResult<R, ERR>

    data class None<R, ERR>(val err: ERR, val message: String = "") : MutationTypedResult<R, ERR>
}

@JvmName("mapToR2")
fun <R, ERR, R2> MTR<R, ERR>.map(dsl: (R) -> R2): MTR<R2, ERR> {
    return when (this) {
        is None -> None(
            err,
            message
        )

        is One -> One(
            dsl(value),
            message
        )
    }
}

@JvmName("mapToMTRR2")
fun <R, ERR, R2> MTR<R, ERR>.mapTo(dsl: (R) -> MTR<R2, ERR>): MTR<R2, ERR> {
    return when (this) {
        is None -> None(
            err,
            message
        )

        is One -> dsl(value)
    }
}