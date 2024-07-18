package cn.llonvne.testjpa.db.result

import cn.llonvne.testjpa.db.internal.user.DisgustingQueryMutationResultShorthandApi

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