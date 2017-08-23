package app.youkai.ui.feature.library_update

import android.content.Context
import app.youkai.R
import app.youkai.data.models.Status

class AnimeStatusResolver : StatusResolver {

    /**
     * These may be translated.
     */
    private lateinit var currentlyWatching: String
    private lateinit var wantToWatch: String
    private lateinit var completed: String
    private lateinit var onHold: String
    private lateinit var dropped: String
    private lateinit var statusTexts: Array<String>

    /**
     * Must be initialised before using any functions.
     */
    override fun init(context: Context) {
        currentlyWatching = context.resources.getString(R.string.currently_watching)
        wantToWatch = context.resources.getString(R.string.want_to_watch)
        completed = context.resources.getString(R.string.completed)
        onHold = context.resources.getString(R.string.on_hold)
        dropped = context.resources.getString(R.string.dropped)
        statusTexts = context.resources.getStringArray(R.array.anime_statuses)
    }

    /**
     * *Must call init() before using this method.*
     * Returns the api based Status object for a given front-end status text.
     * @param statusText ..etc
     */
    override fun getItemStatus(statusText: String): Status {
        when (statusText) {
            currentlyWatching -> return Status.CURRENT
            wantToWatch -> return Status.PLANNED
            completed -> return Status.COMPLETED
            onHold -> return Status.ON_HOLD
            dropped -> return Status.DROPPED
            else -> throw IllegalArgumentException("No status matched to the statusText: $statusText ")
        }
    }

    /**
     * *Must call init() before using this method.*
     * Returns the front-end status text for a given api based Status object.
     * @param status The status for which text is needed.
     */
    override fun getItemPosition(status: Status): Int {
        val statusText: String
        when (status.value) {
            Status.CURRENT.value -> statusText = currentlyWatching
            Status.PLANNED.value -> statusText = wantToWatch
            Status.COMPLETED.value -> statusText = completed
            Status.ON_HOLD.value -> statusText = onHold
            Status.DROPPED.value -> statusText = dropped
            else -> throw IllegalArgumentException("No statusText matched to the status: " + status.value)
        }
        if (!statusTexts.contains(statusText)) throw IllegalArgumentException("No statusText in the array corresponding to: $statusText")
        return statusTexts.indexOf(statusText)
    }

}