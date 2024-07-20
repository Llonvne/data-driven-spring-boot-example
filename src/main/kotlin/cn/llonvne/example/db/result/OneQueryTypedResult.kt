package cn.llonvne.example.db.result

import cn.llonvne.example.db.internal.user.DisgustingQueryMutationResultShorthandApi

@DisgustingQueryMutationResultShorthandApi
typealias OQTR<E, ERR> = OneQueryTypedResult<E, ERR>
@DisgustingQueryMutationResultShorthandApi
typealias OQTR_O<E, ERR> = OneQueryTypedResult.One<E, ERR>
@DisgustingQueryMutationResultShorthandApi
typealias OQTR_N<E, ERR> = OneQueryTypedResult.None<E, ERR>


/**
 * Represent a result of the query with a specific error type.
 */
sealed interface OneQueryTypedResult<R, ERR> {
    data class One<R, ERR>(val value: R, val message: String = "") : OneQueryTypedResult<R, ERR>

    data class None<R, ERR>(val err: ERR, val message: String = "") : OneQueryTypedResult<R, ERR>

    companion object {
        @JvmName("mapToR2")
        fun <R, ERR, R2> OneQueryTypedResult<R, ERR>.map(dsl: (R) -> R2): OneQueryTypedResult<R2, ERR> {
            return when (this) {
                is None -> None(err, message)
                is One -> One(dsl(value), message)
            }
        }

        @JvmName("mapToOQTR")
        fun <R, ERR, R2> OneQueryTypedResult<R, ERR>.map(dsl: (R) -> OneQueryTypedResult<R2, ERR>): OneQueryTypedResult<R2, ERR> {
            return when (this) {
                is None -> None(err, message)
                is One -> dsl(value)
            }
        }
    }
}

