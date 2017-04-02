package app.youkai.ui.feature.media.summary

import app.youkai.App
import app.youkai.R
import app.youkai.data.models.*
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter

/**
 * Media summary presenter that takes care of all the common functions.
 */
open class BaseSummaryPresenter : MvpBasePresenter<SummaryView>(), SummaryPresenter {
    internal var media: BaseMedia? = null
    internal var onTablet: Boolean = false

    override fun attachView(view: SummaryView?) {
        super.attachView(view)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
    }

    override fun load(media: BaseMedia, onTablet: Boolean) {
        this.media = media
        this.onTablet = onTablet

        setSynopsis()
        setGenres()
        setLength()
        setStreamers()
        setReleaseInfo()
        setCommunityRating()
        setRatingsCount()
        setFavoritesCount()
        setPopularityRank()
        setRatingsRank()
        setReviews()
        setProducers()
        setCharacters()
        setRelated()
    }

    override fun setSynopsis() {
        view?.setSynopsis(media?.synopsis ?: App.context.resources.getString(R.string.info_no_synopsis))
    }

    override fun setGenres() {
        val genres = media?.genres ?: arrayListOf()
        if (genres.isNotEmpty()) {
            view?.setGenres(genres)
        } else {
            showNoGenres()
        }
    }

    override fun showNoGenres() {
        view?.setNoGenres()
    }

    override fun setLength() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun setStreamers() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun setReleaseInfo() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun setCommunityRating() {
        view?.setCommunityRating(media?.averageRating ?: 0f)
    }

    override fun setRatingsCount() {
        // TODO: Get directly from field when Nuck adds to the API response
        val ratingsCount = media?.ratingFrequencies?.run {
            this.filter {
                arrayOf("0.5", "1.0", "1.5", "2.0", "2.5", "3.0", "3.5", "4.0", "4.5", "5.0")
                        .contains(it.key)
            }.values.sumBy(String::toInt)
        } ?: 0
        view?.setRatingsCount(ratingsCount)
    }

    override fun setFavoritesCount() {
        view?.setFavoritesCount(media?.favoritesCount ?: 0)
    }

    override fun setPopularityRank() {
        view?.setPopularityRank(media?.popularityRank ?: -9999) // TODO :^)
    }

    override fun setRatingsRank() {
        view?.setRatingRank(media?.ratingRank ?: 0)
    }

    override fun setReviews() {
        if (media?.reviews?.isNotEmpty() ?: false) {
            view?.setReviews(media?.reviews ?: arrayListOf())
        } else {
            showNoReviews()
        }
    }

    override fun showNoReviews() {
        view?.setNoReviews()
    }

    override fun setProducers() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun setCharacters() {
        throw NotImplementedError("This is media-specific functionality and has to be" +
                " implemented in a subclassing presenter.")
    }

    override fun setRelated() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun onSynopsisClicked() {
        view?.onSynopsisClicked(media?.canonicalTitle ?: "?", media?.synopsis ?: "?") // TODO
    }

    override fun onGenreClicked(genre: Genre) {
        view?.onGenreClicked(genre.slug ?: "") // TODO: Null handling
    }

    override fun onLengthClicked() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun onStreamerClicked(service: Any) {
        view?.onStreamingServiceClicked(/*service.url*/"") // TODO
    }

    override fun onReleaseInfoClicked() {
        throw NotImplementedError("This is media-specific functionality and has to be implemented" +
                " in a subclassing presenter.")
    }

    override fun onReviewClicked(id: String) {
        view?.onReviewClicked(id)
    }

    override fun onAllReviewsClicked() {
        view?.onAllReviewsClicked()
    }

    override fun onCharacterClicked(character: Character) {
        view?.onCharacterClicked(character.id ?: "") // TODO: Null handling
    }

    override fun onMoreCharactersClicked() {
        view?.onAllCharactersClicked()
    }

    override fun onRelatedClicked(id: String) {
        view?.onRelatedClicked(id)
    }
}