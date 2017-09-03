package app.youkai.ui.feature.library_update

import android.content.Context
import app.youkai.R
import app.youkai.data.models.Status

class AnimeStatusResolver(context: Context) : StatusResolver {

    /**
     * These may be translated.
     */
    private var currentlyWatching = context.resources.getString(R.string.currently_watching)
    private var wantToWatch = context.resources.getString(R.string.want_to_watch)
    private var completed: String = context.resources.getString(R.string.completed)
    private var onHold = context.resources.getString(R.string.on_hold)
    private var dropped = context.resources.getString(R.string.dropped)
    private var statusTexts = context.resources.getStringArray(R.array.anime_statuses)

    /**
     * Returns the api based Status object for a given front-end status text.
     * @param statusText ..etc
     */
    override fun getItemStatus(statusText: String) = when (statusText) {
        currentlyWatching -> Status.CURRENT
        wantToWatch -> Status.PLANNED
        completed -> Status.COMPLETED
        onHold -> Status.ON_HOLD
        dropped -> Status.DROPPED
        else -> throw IllegalArgumentException("No status matched to the statusText: $statusText")
    }

    /**
     * *Must call init() before using this method.*
     * Returns the front-end status text for a given api based Status object.
     * @param status The status for which text is needed.
     */
    override fun getItemPosition(status: Status): Int {
        val statusText = when (status.value) {
            Status.CURRENT.value -> currentlyWatching
            Status.PLANNED.value -> wantToWatch
            Status.COMPLETED.value -> completed
            Status.ON_HOLD.value -> onHold
            Status.DROPPED.value -> dropped
            else -> throw IllegalArgumentException("No statusText matched to the status: " + status.value)
        }
        if (!statusTexts.contains(statusText)) throw IllegalArgumentException("No statusText in the array corresponding to: $statusText")
        return statusTexts.indexOf(statusText)
    }

}
