package app.youkai.util

import android.support.annotation.IntegerRes
import app.youkai.App

/**
 * Convenience function to get colors from anywhere
 */
fun color(@IntegerRes res: Int) = App.context.resources.getColor(res)