package cn.llonvne.example.db.result


/**
 * Assert the result is [cn.llonvne.example.db.result.MutationResult.Success] variant
 */
fun <E> MutationResult<E>.assertSuccess(): MutationResult.Success<E> {
    return this as MutationResult.Success<E>
}

/**
 * Assert the result is [cn.llonvne.example.db.result.MutationResult.Success] variant and return the value property
 */
fun <E> MutationResult<E>.unwrap(): E {
    return assertSuccess().value
}