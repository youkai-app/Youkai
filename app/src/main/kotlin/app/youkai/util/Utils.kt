package app.youkai.util

import android.support.annotation.IntegerRes
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import app.youkai.App

/**
 * Convenience function to get colors from anywhere
 */
fun color(@IntegerRes res: Int) = App.context.resources.getColor(res)

/**
 * Convenience function to get strings from anywhere
 */
fun string(@IntegerRes res: Int): String = App.context.resources.getString(res)

/**
 * Colors a given span in a string and returns a SpannableString
 */
fun coloredSpannableString(text: String, color: Int, start: Int, end: Int): SpannableString {
    val spannable = SpannableString(text)
    spannable.setSpan(ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    return spannable
}