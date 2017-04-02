package app.youkai.ui.feature.media.view

import android.support.annotation.IntegerRes
import android.view.View
import app.youkai.App
import app.youkai.R
import app.youkai.util.ext.getLayoutInflater
import kotlinx.android.synthetic.main.item_nothing.view.*

/**
 * A simple helper class to allow easy item creation behind the scenes.
 *
 * See https://i.imgur.com/7ph2tGG.png
 */
object NothingHereItemCreator {

    /**
     * Inflates a new item, sets its text to a given string, and returns the resulting view.
     */
    fun new(text: String): View {
        val view = App.context.getLayoutInflater().inflate(R.layout.item_nothing, null, false)
        view.text.text = text
        return view
    }

    fun new(@IntegerRes resource: Int): View = new(App.context.getString(resource))
}