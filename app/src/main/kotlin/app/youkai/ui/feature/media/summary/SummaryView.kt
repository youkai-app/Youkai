package app.youkai.ui.feature.media.summary

import android.support.annotation.IntegerRes
import app.youkai.data.models.*
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.common.mvp.MvpLceView

interface SummaryView : MvpLceView {
    fun setSynopsis(synopsis: String)
    fun setCategories(categories: List<Category>)
    fun setNoCategories()
    fun setLength(firstLine: String, secondLine: String, makeLink: Boolean)
    fun setLengthIcon(@IntegerRes res: Int)
    fun setStreamers(streamers: List<StreamingLink>)
    fun setStremersShown(show: Boolean = true)
    fun setNoStreamingServices()
    fun setReleaseInfo(firstLine: String, secondLine: String, makeLink: Boolean)
    fun setCommunityRating(rating: Float)
    fun setRatingsCount(count: Int)
    fun setFavoritesCount(count: Int)
    fun setPopularityRank(rank: Int)
    fun setRatingRank(rank: Int)
    fun setReviews(reviews: List<Review>)
    fun setNoReviews()
    fun setProducers(producers: String)
    fun setProducersIcon(@IntegerRes res: Int)
    fun setCharacters(characters: List<Character?>)
    fun setCharactersShown(show: Boolean = true)
    fun setMoreCharactersCount(count: Int)
    fun setRelated(related: List<MediaRelationship>?)
    fun setRelatedVisible(show: Boolean = true)

    fun onSynopsisClicked(title: String, synopsis: String)
    fun onStreamingServiceClicked(url: String)
    fun onLengthClicked()
    fun onReleaseInfoClicked()
    fun onReviewClicked(id: String)
    fun onAllReviewsClicked()
    fun onCharacterClicked(id: String)
    fun onAllCharactersClicked()
    fun onCategoryClicked(slug: String)
    fun startRelatedMediaActivity(id: String, type: MediaType)

    fun startCharactersActivity(mediaId: String, mediaType: MediaType, mediaTitle: String)
}