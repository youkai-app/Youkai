package app.youkai.ui.feature.media

import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState

/**
 * TODO: Implement when models are parcelable
 */
class MediaState : RestorableViewState<MediaView> {

    override fun apply(view: MediaView, retained: Boolean) {

    }

    override fun restoreInstanceState(bundle: Bundle?): RestorableViewState<MediaView> {
        val state = MediaState()
        if (bundle != null) {

        }
        return state
    }

    override fun saveInstanceState(bundle: Bundle) {

    }

    companion object {

    }
}