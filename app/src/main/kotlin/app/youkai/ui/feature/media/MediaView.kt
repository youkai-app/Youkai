package app.youkai.ui.feature.media

import android.support.annotation.IntegerRes
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Titles
import com.hannesdorfmann.mosby.mvp.MvpView

interface MediaView : MvpView {
    fun setSummary(media: BaseMedia)

    fun setPoster(url: String?)
    fun setCover(url: String?)
    fun setTitle(title: String)
    fun setFavorited(favorited: Boolean = true)
    fun setType(type: String)
    fun setReleaseSummary(summary: String)
    fun setAgeRating(rating: String)
    fun setFabIcon(@IntegerRes res: Int)
    fun enableFab(enable: Boolean = true)

    fun showFullscreenCover()
    fun showFullscreenPoster()
    fun showAlternativeTitles(titles: Titles?, abbreviatedTitles: List<String>?)
    fun showTrailer(videoId: String)
    fun showLibraryEdit()
    fun showToast(text: String)

    fun tellChildrenLoading()
    fun tellChildrenContent()
    fun tellChildrenError(e: Throwable)

    fun onTablet(): Boolean

    enum class FabState {
        LOADING, ADD, EDIT
    }
}