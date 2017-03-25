package app.youkai.util.ext

import android.widget.EditText

/**
 * Various extension functions for various purposes that don't really belong in a specific file.
 */

/**
 * Hack to make it possible to use functions with return types in places where Unit is expected.
 */
fun Any.unitify() {
    /* do nothing */
}

/**
 * Appends a string to a string.
 */
fun String.append(other: String, delimiter: String = ""): String {
    return if (!isEmpty()) plus(delimiter).plus(other) else other
}

/**
 * Appends a string to an entry in a MutableMap.
 */
fun MutableMap<String, String>.append(key: String, other: String, delimiter: String = ""): MutableMap<String, String> {
    put(key, (this[key] ?: "").append(other, delimiter))
    return this
}

/**
 * Returns the input as a string.
 */
fun EditText.inputString(): String = text.toString()

