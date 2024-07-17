package cn.llonvne.testjpa.db.result

/**
 * Represent a result of the query.
 */
sealed interface OneQueryResult<E> {
    data class One<E>(val value: E, val message: String = "") : OneQueryResult<E>

    data class None<E>(val message: String) : OneQueryResult<E>
}