package app.youkai.ui.feature.media

import app.youkai.data.models.BaseMedia
import app.youkai.data.models.LibraryEntry

interface MediaPresenter {
    fun set(mediaId: String)
    fun loadMedia(mediaId: String)
    fun loadLibraryInfo(mediaId: String)
    fun setMedia(media: BaseMedia)
    fun setLibraryInfo(libraryEntry: LibraryEntry?)

    fun setPoster()
    fun setCover()
    fun setTitle()
    fun setFavorited()
    fun setType()
    fun setReleaseSummary()
    fun setAgeRating()
    fun setFabState(state: MediaView.FabState)

    fun onPosterClicked()
    fun onCoverClicked()
    fun onTitleClicked()
    fun onAlternativeTitlesClicked()
    fun onFavoriteClicked()
    fun onTrailerClicked()
    fun onFabClicked()
}