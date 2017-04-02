package app.youkai.ui.feature.media.view

import android.view.View
import app.youkai.App.Companion.context
import app.youkai.R
import app.youkai.data.models.Review
import app.youkai.util.ext.getLayoutInflater
import app.youkai.util.ext.loadAnimated
import kotlinx.android.synthetic.main.item_media_review.view.*

/**
 * A simple helper class to allow easy item creation behind the scenes.
 */
object MediaReviewItemCreator {

    /**
     * Inflates a new media review item, sets its data to given data, and returns the resulting view.
     */
    fun new(review: Review): View {
        val view = context.getLayoutInflater().inflate(R.layout.item_media_review, null, false)

        view.avatar.loadAnimated(review.user?.avatar?.medium)
        view.username.text = review.user?.name
        view.rating.rating = review.rating ?: 0f
        view.review.text = review.content

        return view
    }
}