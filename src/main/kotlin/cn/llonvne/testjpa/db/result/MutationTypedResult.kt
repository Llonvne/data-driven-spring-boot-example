package cn.llonvne.testjpa.db.result

sealed interface MutationTypedResult<R, ERR> {
    data class One<R, ERR>(val value: R, val message: String = "") : MutationTypedResult<R, ERR>

    data class None<R, ERR>(val err: ERR, val message: String = "") : MutationTypedResult<R, ERR>
}