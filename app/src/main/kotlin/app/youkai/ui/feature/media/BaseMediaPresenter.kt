package app.youkai.ui.feature.media

import app.youkai.R
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.LibraryEntry
import app.youkai.data.models.ext.releaseSummary
import app.youkai.util.string
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Top-level media presenter that takes care of all common and core functions.
 */
open class BaseMediaPresenter : MvpBasePresenter<MediaView>(), MediaPresenter {
    lateinit var mediaId: String
    internal var media: BaseMedia? = null
    internal var libraryEntry: Any? = null

    internal var fabState = MediaView.FabState.LOADING

    override fun set(mediaId: String) {
        this.mediaId = mediaId
        loadMedia(mediaId)
        loadLibraryInfo(mediaId)
    }

    override fun loadMedia(mediaId: String) {
        throw NotImplementedError("This is media-specific functionality and has to be" +
                " implemented in a subclassing presenter.")
    }

    override fun loadLibraryInfo(mediaId: String) {
        // TODO: Get from API
    }

    override fun setMedia(media: BaseMedia?) {
        this.media = media

        // Let summary handle null media internally
        view?.setSummary(media)

        // Exclude these from the check below since they have proper error states
        setPoster()
        setCover()

        if (media != null) {
            setTitle()
            setFavorited()
            setType()
            setReleaseSummary()
            setAgeRating()
        }

        view?.setAlternativeTitlesButtonVisible(media?.titles != null
                || media?.abbreviatedTitles?.isNotEmpty() ?: false)
    }

    override fun setLibraryInfo(libraryEntry: LibraryEntry?) {
        if (libraryEntry == null) {
            setFabState(MediaView.FabState.ADD)
        }
        // TODO: Implement
    }

    override fun setPoster() {
        view?.setPoster(media?.posterImage?.original)
    }

    override fun setCover() {
        view?.setCover(media?.coverImage?.original)
    }

    override fun setTitle() {
        view?.setTitle(media?.canonicalTitle ?: "?")
    }

    override fun setFavorited() {
        // TODO: Get favorites from API and filter[item_type]=Anime&filter[item_id]=3919&filter[user_id]=41809, in the subclassing presenters
    }

    override fun setType() {
        throw NotImplementedError("This is media-specific functionality and has to be" +
                " implemented in a subclassing presenter.")
    }

    override fun setReleaseSummary() {
        view?.setReleaseSummary(media?.releaseSummary() ?: "")
    }

    override fun setAgeRating() {
        view?.setAgeRating("${media?.ageRating ?: ""} ${media?.ageRatingGuide ?: ""}")
    }

    override fun setFabState(state: MediaView.FabState) {
        this.fabState = state

        when (state) {
            MediaView.FabState.LOADING -> {
                view?.enableFab(false)
            }
            MediaView.FabState.ADD -> {
                view?.enableFab()
                view?.setFabIcon(R.drawable.ic_library_add_white_24dp)
            }
            MediaView.FabState.EDIT -> {
                view?.enableFab()
                view?.setFabIcon(R.drawable.ic_library_edit_white_24dp)
            }
        }
    }

    override fun onMediaError(e: Throwable) {
        view?.showErrorSnackbar(e.message ?: string(R.string.oops), {
            onMediaRetryClicked()
        })
    }

    override fun onLibraryEntryError(e: Throwable) {
        view?.showErrorSnackbar(e.message ?: string(R.string.oops), {
            onLibraryEntryRetryClicked()
        })
    }

    override fun onPosterClicked() {
        view?.showFullscreenPoster()
    }

    override fun onCoverClicked() {
        view?.showFullscreenCover()
    }

    override fun onTitleClicked() {
        if (media?.canonicalTitle != null) {
            view?.showToast(media?.canonicalTitle!!)
        }
    }

    override fun onAlternativeTitlesClicked() {
        view?.showAlternativeTitles(media?.titles, media?.abbreviatedTitles)
    }

    override fun onFavoriteClicked() {
        // TODO: Mark favorite
    }

    override fun onTrailerClicked() {
        throw NotImplementedError("This is media-specific functionality and has to be" +
                " implemented in a subclassing presenter.")
    }

    override fun onFabClicked() {
        when (fabState) {
            MediaView.FabState.ADD -> {
                // TODO: Options to add to library
            }
            MediaView.FabState.EDIT -> {
                view?.showLibraryEdit()
            }
            else -> throw IllegalStateException("FAB state is indeterminate.")
        }
    }

    override fun onMediaRetryClicked() {
        loadMedia(mediaId)
    }

    override fun onLibraryEntryRetryClicked() {
        loadLibraryInfo(mediaId)
    }
}