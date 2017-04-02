package app.youkai.ui.feature.media.view

import android.graphics.PorterDuff.Mode.SRC_ATOP
import android.view.View
import android.widget.ImageView
import app.youkai.App.Companion.context
import app.youkai.R
import app.youkai.data.models.StreamingLink
import app.youkai.util.color
import app.youkai.util.ext.getLayoutInflater

/**
 * A simple helper class to allow easy item creation behind the scenes.
 */
object StreamerItemCreator {

    /**
     * Inflates a new streaming service item, sets its values to a given object, and returns the resulting view.
     */
    fun new(service: StreamingLink): View {
        val view = context.getLayoutInflater().inflate(R.layout.item_streamer, null, false) as ImageView

        when (service.streamer?.siteName ?: "") {
            "Anime Network" -> {
                view.setImageResource(R.drawable.ic_streamer_anime_network_white_24dp)
                view.setColorFilter(color(R.color.streamer_anime_network), SRC_ATOP)
            }
            "Animelab" -> {
                view.setImageResource(R.drawable.ic_streamer_animelab_white_24dp)
                view.setColorFilter(color(R.color.streamer_animelab), SRC_ATOP)
            }
            "Crunchyroll" -> {
                view.setImageResource(R.drawable.ic_streamer_crunchyroll_white_24dp)
                view.setColorFilter(color(R.color.streamer_crunchyroll), SRC_ATOP)
            }
            "Daisuki" -> {
                view.setImageResource(R.drawable.ic_streamer_daisuki_white_24dp)
                view.setColorFilter(color(R.color.streamer_daisuki), SRC_ATOP)
            }
            "Funimation" -> {
                view.setImageResource(R.drawable.ic_streamer_funimation_white_24dp)
                view.setColorFilter(color(R.color.streamer_funimation), SRC_ATOP)
            }
            "Hulu" -> {
                view.setImageResource(R.drawable.ic_streamer_hulu_white_24dp)
                view.setColorFilter(color(R.color.streamer_hulu), SRC_ATOP)
            }
            "Netflix" -> {
                view.setImageResource(R.drawable.ic_streamer_netflix_white_24dp)
                view.setColorFilter(color(R.color.streamer_netflix), SRC_ATOP)
            }
            "Viewster" -> {
                view.setImageResource(R.drawable.ic_streamer_viewster_white_24dp)
                view.setColorFilter(color(R.color.streamer_viewster), SRC_ATOP)
            }
        }

        return view
    }
}