package cn.llonvne.example.db.result

import cn.llonvne.example.db.internal.user.DisgustingQueryMutationResultShorthandApi

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

    companion object {
        fun <R, R2> MR<R>.map(dsl: (R) -> R2): MR<R2> {
            return when (this) {
                is Failed -> Failed(message)
                is Success -> Success(dsl(value), message)
            }
        }

        fun <R, R2> MR<R>.mapTo(dsl: (R) -> MR<R2>): MR<R2> {
            return when (this) {
                is Failed -> Failed(message)
                is Success -> dsl(value)
            }
        }
    }
}


