package cn.llonvne.testjpa.db.result

import cn.llonvne.testjpa.db.internal.user.DisgustingQueryMutationResultShorthandApi

@DisgustingQueryMutationResultShorthandApi
typealias MR<E> = MutationResult<E>
@DisgustingQueryMutationResultShorthandApi
typealias MR_O<E> = MutationResult.Success<E>
@DisgustingQueryMutationResultShorthandApi
typealias MR_N<E> = MutationResult.Failed<E>

/**
 * Represent a result of the Mutation
 */
sealed interface MutationResult<E> {
    data class Success<E>(val value: E, val message: String = "") : MutationResult<E>

    data class Failed<E>(val message: String) : MutationResult<E>
}


