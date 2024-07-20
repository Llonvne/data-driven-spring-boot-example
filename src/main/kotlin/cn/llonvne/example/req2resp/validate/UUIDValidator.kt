package cn.llonvne.example.req2resp.validate

import cn.llonvne.example.req2resp.validate.ValidateRequestDsl.PassFunction
import java.util.UUID
import kotlin.reflect.KProperty

fun <R> ValidateRequestDsl<R>.ofUUID(property: KProperty<String>, failMessage: String = "must be a valid UUID") {
    val passResult = isUUID(property.call())
    if (!passResult) {
        result += PassFunction(property, failMessage)
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