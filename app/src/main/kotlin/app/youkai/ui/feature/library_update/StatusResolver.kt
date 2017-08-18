package app.youkai.ui.feature.library_update

import android.content.Context
import app.youkai.data.models.Status

interface StatusResolver {

    fun init(context: Context)

    fun getItemStatus(statusText: String): Status

    fun getItemPosition(status: Status): Int

}