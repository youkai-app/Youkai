package app.youkai.util.ext

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Various extension functions for timestamp-related purposes
 */

/**
 * Assumes the string is in 2017-12-25 format, converts it into a UNIX timestamp, and returns it.
 */
fun String.toTimestamp(): Long {
    val parts = split("-")
    val cal = GregorianCalendar(parts[0].toInt(), parts[1].toInt(), parts[2].toInt())
    return cal.timeInMillis
}

/**
 * Assumes this long is a UNIX timestamp and strips it of its hour, minute, second, and millisecond
 * values, then returns the resulting timestamp.
 */
fun Long.stripToDate(): Long {
    val cal = GregorianCalendar()
    cal.timeInMillis = this
    return cal.stripToDate().timeInMillis
}

/**
 * Strips this calendar of its hour, minute, second, and millisecond values, then returns the
 * resulting Calendar object.
 */
fun Calendar.stripToDate(): Calendar {
    set(Calendar.HOUR_OF_DAY, 0)
    set(Calendar.HOUR, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MILLISECOND, 0)
    return this
}

/**
 * Assumes this is a UNIX timestamp and returns a formatted String from it.
 *
 * Example: Jun 2, 2017
 */
@SuppressLint("SimpleDateFormat")
fun Long.toMonthDayYearString(): String {
    return SimpleDateFormat("MMM d, yyyy").format(this)
}