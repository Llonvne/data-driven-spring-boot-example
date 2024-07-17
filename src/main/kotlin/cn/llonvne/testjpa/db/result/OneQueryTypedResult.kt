package cn.llonvne.testjpa.db.result

sealed interface OneQueryTypedResult<R, ERR> {
    data class One<R, ERR>(val value: R, val message: String = "") : OneQueryTypedResult<R, ERR>

    data class None<R, ERR>(val err: ERR, val message: String = "") : OneQueryTypedResult<R, ERR>
}
