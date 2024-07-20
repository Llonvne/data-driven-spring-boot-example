package cn.llonvne.example.req2resp.validate

import java.util.UUID
import kotlin.reflect.KProperty

fun <R> ValidateRequestDsl<R>.ofUUID(property: KProperty<String>, failMessage: String = "must be a valid UUID") {
    of(property, failMessage) {
        isUUID(it)
    }
}

private fun isUUID(value: String): Boolean {
    try {
        UUID.fromString(value)
    } catch (e: IllegalArgumentException) {
        return false
    }
    return true
}