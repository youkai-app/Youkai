package app.youkai.ui.feature.media.view

import android.view.View
import android.widget.TextView
import app.youkai.App
import app.youkai.R
import app.youkai.data.models.Genre
import app.youkai.util.ext.getLayoutInflater

/**
 * A simple helper class to allow easy item creation behind the scenes.
 */
object GenreItemCreator {

    /**
     * Inflates a new genre item, sets its text to a given string, and returns the resulting view.
     *
     */
    fun new(genre: Genre): View {
        val view = App.context.getLayoutInflater().inflate(R.layout.item_genre, null, false)
        (view as TextView).text = genre.name
        return view
    }
}