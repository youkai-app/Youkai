package app.youkai.util.ext

/**
 * Various extension functions for various purposes that don't really belong in a specific file.
 */

/**
 * Hack to make it possible to use functions with return types in places where Unit is expected.
 */
fun Any.unitify() {
    /* do nothing */
}

fun String.append(other: String, delimiter: String = ""): String {
    return if (!isEmpty()) plus(delimiter).plus(other) else other
}

fun MutableMap<String, String>.append(key: String, other: String, delimiter: String = ""): MutableMap<String, String> {
    put(key, (this[key] ?: "").append(other, delimiter))
    return this
}