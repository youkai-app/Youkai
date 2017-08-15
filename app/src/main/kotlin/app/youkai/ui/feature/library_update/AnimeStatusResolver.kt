package app.youkai.ui.feature.library_update

import android.content.Context
import app.youkai.R
import app.youkai.data.models.Status

object AnimeStatusResolver {

    /**
     * These may be translated.
     */
    private lateinit var currentlyWatching: String
    private lateinit var wantToWatch: String
    private lateinit var completed: String
    private lateinit var onHold: String
    private lateinit var dropped: String

    /**
     * must be initialised before using any functions
     */
    fun init(context: Context) {
        currentlyWatching = context.getString(R.string.currently_watching)
        wantToWatch = context.getString(R.string.want_to_watch)
        completed = context.getString(R.string.completed)
        onHold = context.getString(R.string.on_hold)
        dropped = context.getString(R.string.dropped)
    }

    /**
     * TODO: docs
     * Must call init() before using this method.
     * @param item ..etc
     */
    fun getItemStatus(item : String): Status {
        when (item) {
            currentlyWatching -> return Status.CURRENT
            wantToWatch -> return Status.PLANNED
            completed -> return Status.COMPLETED
            onHold -> return Status.ON_HOLD
            dropped -> return Status.DROPPED
            else -> throw IllegalArgumentException("No status matched to the item: $item ")
        }
    }

}
