package app.youkai.util.ext

import android.support.design.widget.Snackbar
import android.view.View

/**
 * Some extension functions that make life with Snackbars nicer.
 *
 * See https://antonioleiva.com/kotlin-awesome-tricks-for-android
 */

inline fun View.snackbar(message: String, length: Int = Snackbar.LENGTH_LONG, f: Snackbar.() -> Unit): Snackbar {
    val snack = Snackbar.make(this, message, length)
    snack.f()
    snack.show()
    return snack
}

fun Snackbar.action(action: String, color: Int? = null, listener: (View) -> Unit) {
    setAction(action, listener)
    color?.let { setActionTextColor(color) }
}
