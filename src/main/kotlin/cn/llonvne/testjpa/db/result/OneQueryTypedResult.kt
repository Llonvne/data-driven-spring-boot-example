package cn.llonvne.testjpa.db.result

/**
 * Represent a result of the query with a specific error type.
 */
sealed interface OneQueryTypedResult<R, ERR> {
    data class One<R, ERR>(val value: R, val message: String = "") : OneQueryTypedResult<R, ERR>

    data class None<R, ERR>(val err: ERR, val message: String = "") : OneQueryTypedResult<R, ERR>
}
