package cn.llonvne.testjpa.db.result

sealed interface MutationResult<E> {
    data class Success<E>(val value: E, val message: String = "") : MutationResult<E>

    data class Failed<E>(val message: String) : MutationResult<E>
}


