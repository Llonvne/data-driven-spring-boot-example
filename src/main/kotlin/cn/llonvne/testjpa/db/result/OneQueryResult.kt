package cn.llonvne.testjpa.db.result

import cn.llonvne.testjpa.db.internal.user.DisgustingQueryMutationResultShorthandApi

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
}