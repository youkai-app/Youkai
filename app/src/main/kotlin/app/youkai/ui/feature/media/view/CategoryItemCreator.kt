package app.youkai.ui.feature.media.view

import android.view.View
import android.widget.TextView
import app.youkai.App
import app.youkai.R
import app.youkai.data.models.Category
import app.youkai.util.ext.getLayoutInflater

/**
 * A simple helper class to allow easy item creation behind the scenes.
 */
object CategoryItemCreator {

    /**
     * Inflates a new category item, sets its text to a given string, and returns the resulting view.
     *
     */
    fun new(category: Category): View {
        val view = App.context.getLayoutInflater().inflate(R.layout.item_category, null, false)
        (view as TextView).text = category.title
        return view
    }
}