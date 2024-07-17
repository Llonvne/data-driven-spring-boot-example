package cn.llonvne.testjpa.db.result

import cn.llonvne.testjpa.db.result.MutationResult.Success

fun <E> MutationResult<E>.assertSuccess(): Success<E> {
    return this as Success<E>
}

fun <E> MutationResult<E>.unwrap(): E {
    return assertSuccess().value
}