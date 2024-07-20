package cn.llonvne.example.db.internal

/**
 * Represent a Converter that converter a internal database entity to public representation
 */
fun interface EntityConverter<IN, OUT> {
    fun convert(input: IN): OUT
}