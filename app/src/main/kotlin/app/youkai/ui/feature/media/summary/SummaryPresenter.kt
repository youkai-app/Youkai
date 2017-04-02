package app.youkai.ui.feature.media.summary

import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Character
import app.youkai.data.models.Genre

interface SummaryPresenter {
    fun load(media: BaseMedia, onTablet: Boolean)

    fun setSynopsis()
    fun setGenres()
    fun showNoGenres()
    fun setLength()
    fun setStreamers()
    fun setReleaseInfo()
    fun setCommunityRating()
    fun setRatingsCount()
    fun setFavoritesCount()
    fun setPopularityRank()
    fun setRatingsRank()
    fun setReviews()
    fun showNoReviews()
    fun setProducers()
    fun setCharacters()
    fun setRelated()

    fun onSynopsisClicked()
    fun onGenreClicked(genre: Genre)
    fun onLengthClicked()
    fun onStreamerClicked(service: Any) // TODO: Real type
    fun onReleaseInfoClicked()
    fun onReviewClicked(id: String)
    fun onAllReviewsClicked()
    fun onCharacterClicked(character: Character)
    fun onMoreCharactersClicked()
    fun onRelatedClicked(id: String)
}