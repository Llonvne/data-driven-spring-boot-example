package cn.llonvne.example.tools

data class RequestKey<E>(val key: String)

inline fun <reified E> key(): RequestKey<E> {
    return RequestKey(E::class.qualifiedName!!)
}

fun <E : Any> storeNotNull(value: E?): Boolean {
    if (value != null) {
        store(value)
        return true
    }
    return false
}

fun <E : Any> store(value: E): E {
    currentRequest().setAttribute(value::class.qualifiedName!!, value)
    return value
}

fun <E : Any> provide(key: RequestKey<E>): E {
    return currentRequest().getAttribute(key.key) as E
}

inline fun <reified E> provide(): E {
    return currentRequest().getAttribute(E::class.qualifiedName) as E
}

fun <E : Any> clear(key: RequestKey<E>) {
    currentRequest().setAttribute(key.key, null)
}

inline fun <reified E> clear() {
    currentRequest().setAttribute(E::class.qualifiedName, null)
}
