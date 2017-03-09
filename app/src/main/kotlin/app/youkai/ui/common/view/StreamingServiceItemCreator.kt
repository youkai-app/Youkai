package app.youkai.ui.common.view

import android.view.View
import app.youkai.App
import app.youkai.R
import app.youkai.util.ext.getLayoutInflater

/**
 * A simple helper class to allow easy item creation behind the scenes.
 */
object StreamingServiceItemCreator {

    /**
     * Inflates a new streaming service item, sets its values to a given object, and returns the resulting view.
     */
    fun new(service: Any): View {
        val view = App.context.getLayoutInflater().inflate(R.layout.item_streaming_service, null, false)
        // TODO: Set stuff
        return view
    }
}