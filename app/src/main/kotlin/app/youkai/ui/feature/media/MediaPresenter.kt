package app.youkai.ui.feature.media

import app.youkai.data.models.BaseMedia
import app.youkai.data.models.LibraryEntry

interface MediaPresenter {
    fun set(mediaId: String, media: BaseMedia?, libraryEntry: LibraryEntry?)
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

    fun onLoading()
    fun onContent()
    fun onError(e: Throwable)
    fun onPosterClicked()
    fun onCoverClicked()
    fun onTitleClicked()
    fun onAlternativeTitlesClicked()
    fun onFavoriteClicked()
    fun onTrailerClicked()
    fun onFabClicked()
}