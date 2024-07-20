package cn.llonvne.example.db.result

import cn.llonvne.example.db.internal.user.DisgustingQueryMutationResultShorthandApi

@DisgustingQueryMutationResultShorthandApi
typealias OQR<E> = OneQueryResult<E>
@DisgustingQueryMutationResultShorthandApi
typealias OQR_O<E> = OneQueryResult.One<E>
@DisgustingQueryMutationResultShorthandApi
typealias OQR_N<E> = OneQueryResult.None<E>

/**
 * Represent a result of the query.
 */
sealed interface OneQueryResult<E> {
    data class One<E>(val value: E, val message: String = "") : OneQueryResult<E>

    data class None<E>(val message: String) : OneQueryResult<E>

    companion object {
        @JvmName("mapToR1")
        fun <R, R1> OneQueryResult<R>.map(dsl: (R) -> R1): OneQueryResult<R1> {
            return when (this) {
                is None -> None(message)
                is One -> One(dsl(value), message)
            }
        }
        @JvmName("mapToOQRR1")
        fun <R, R1> OneQueryResult<R>.map(dsl: (R) -> OneQueryResult<R1>): OneQueryResult<R1> {
            return when (this) {
                is None -> None(message)
                is One -> dsl(value)
            }
        }
    }

    fun isOne(): Boolean {
        return this is One<E>
    }
}