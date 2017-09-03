package app.youkai.ui.feature.library_update

import app.youkai.data.models.Status

interface StatusResolver {

    fun getItemStatus(statusText: String): Status

    fun getItemPosition(status: Status): Int

}