package app.youkai.data.models.ext

import app.youkai.data.models.Casting

/**
 * Casting extension functions
 */

/**
 * Checks if this cast member is a mangaka (aka author)
 */
fun Casting.isMangaka(): Boolean {
    return role == "Art" || role == "Story" || role == "Story & Art"
}
