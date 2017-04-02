package app.youkai.ui.feature.media.summary

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Bundle
import android.support.annotation.IntegerRes
import android.text.Html
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import app.youkai.R
import app.youkai.data.models.*
import app.youkai.ui.feature.media.view.*
import app.youkai.util.ext.formatForDisplay
import app.youkai.util.ext.formatTwoDecimals
import app.youkai.util.ext.toTimestamp
import app.youkai.util.ext.toVisibility
import com.hannesdorfmann.mosby.mvp.MvpFragment
import kotlinx.android.synthetic.main.activity_media.*
import kotlinx.android.synthetic.main.fragment_media_summary.*

class SummaryFragment : MvpFragment<SummaryView, BaseSummaryPresenter>(), SummaryView {
    var streamingServiceClickListener: StreamingServiceClickListener? = null
    var episodesClickListener: SimpleClickListener? = null
    var seasonClickListener: SimpleClickListener? = null
    var reviewsClickListener: SimpleClickListener? = null
    var charactersClickListener: SimpleClickListener? = null

    override fun createPresenter(): BaseSummaryPresenter = BaseSummaryPresenter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_media_summary, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        synopsisContainer.setOnClickListener {
            presenter.onSynopsisClicked()
        }

        length.setOnClickListener {
            presenter.onLengthClicked()
        }

        releaseInfo.setOnClickListener {
            presenter.onReleaseInfoClicked()
        }

        allReviewsButton.setOnClickListener {
            presenter.onAllReviewsClicked()
        }
    }

    fun setMedia(media: BaseMedia, onTablet: Boolean) { // TODO: Real type
        setPresenter(
                when (media) {
                    is Anime -> AnimeSummaryPresenter()
                    is Manga -> MangaSummaryPresenter()
                    else -> BaseSummaryPresenter()
                }
        )
        presenter.attachView(this)
        presenter.load(media, onTablet)
    }

    override fun setSynopsis(synopsis: String) {
        this.synopsis.text = synopsis
    }

    override fun setGenres(genres: List<Genre>) {
        genresContainer.removeAllViews()
        for (genre in genres) {
            val view = GenreItemCreator.new(genre)
            view.setOnClickListener {
                presenter.onGenreClicked(genre)
            }
            genresContainer.addView(view)
        }
    }

    override fun setNoGenres() {
        genresContainer.removeAllViews()
        genresContainer.addView(NothingHereItemCreator.new(R.string.info_no_genres_yet))
    }

    override fun setLength(firstLine: String, secondLine: String, makeLink: Boolean) {
        val first = if (makeLink) SpannableString("<a href=\"\">$firstLine</a>") else firstLine
        length.setText(Html.fromHtml("$first<br>$secondLine").toString())
    }

    override fun setLengthIcon(res: Int) {
        length.setIcon(res)
    }

    override fun setStreamers(streamers: List<StreamingLink>) {
        streamingServicesContainer.removeAllViews()
        for (streamer in streamers) {
            val view = StreamerItemCreator.new(streamer)
            view.setOnClickListener {
                presenter.onStreamerClicked(streamer)
            }
            streamingServicesContainer.addView(view)
        }
    }

    override fun setStremersShown(show: Boolean) {
        streamingServicesContainer.visibility = show.toVisibility()
    }

    override fun setNoStreamingServices() {
        streamingServicesContainer.removeAllViews()
        streamingServicesContainer.addView(NothingHereItemCreator.new(R.string.info_no_streaming_services))
    }

    override fun setReleaseInfo(firstLine: String, secondLine: String, makeLink: Boolean) {
        val second = if (makeLink) SpannableString("<a href=\"\">$secondLine</a>") else secondLine
        releaseInfo.setText(Html.fromHtml("$firstLine<br>$second").toString())
    }

    override fun setCommunityRating(rating: Float) {
        communityRatingText.text = rating.formatTwoDecimals()
        communityRating.rating = rating
    }

    override fun setRatingsCount(count: Int) {
        ratingsCount.text = context.resources.getQuantityString(
                R.plurals.x_ratings,
                count,
                count.formatForDisplay()
        )
    }

    override fun setFavoritesCount(count: Int) {
        favoritesCount.text = context.resources.getQuantityString(
                R.plurals.x_favorites,
                count,
                count.formatForDisplay()
        )
    }

    @SuppressLint("SetTextI18n")
    override fun setPopularityRank(rank: Int) {
        popularityRank.text = "#$rank"
    }

    @SuppressLint("SetTextI18n")
    override fun setRatingRank(rank: Int) {
        ratingRank.text = "#$rank"
    }

    override fun setReviews(reviews: List<Review>) {
        reviewsContainer.visibility = VISIBLE
        allReviewsButton.visibility = VISIBLE

        reviewsContainer.removeAllViews()
        for (i in 0..1) {
            val review = reviews.getOrNull(i) ?: return
            val view = MediaReviewItemCreator.new(review)
            view.setOnClickListener {
                if (review.id != null) {
                    presenter.onReviewClicked(review.id!!)
                }
            }
            reviewsContainer.addView(view)
        }
    }

    override fun setNoReviews() {
        reviewsContainer.removeAllViews()
        reviewsContainer.visibility = GONE
        allReviewsButton.visibility = GONE
    }

    override fun setProducers(producers: String) {
        this.producers.setText(producers)
    }

    override fun setProducersIcon(res: Int) {
        producers.setIcon(res)
    }

    override fun setCharacters(characters: List<Character?>) {
        this.characters.setCharacters(characters)
    }

    override fun setCharactersShown(show: Boolean) {
        charactersLabel.visibility = show.toVisibility()
        characters.visibility = show.toVisibility()
    }

    override fun setMoreCharactersCount(count: Int) {
        characters.setMoreCount(count)
    }

    override fun setRelated(related: List<MediaRelationship>?) {
        if (related == null) return

        // Make sure the container and the label are visible
        setRelatedVisible()

        this.related.removeAllViews()
        for (item in related) {
            val view = RelatedMediaItemCreator.new(item)
            view.setOnClickListener {
                if (item.id != null) {
                    presenter.onRelatedClicked(item.id!!)
                }
            }
            this.related.addView(view)
        }
    }

    override fun setRelatedVisible(show: Boolean) {
        this.relatedLabel.visibility = show.toVisibility()
        this.related.visibility = show.toVisibility()
    }

    override fun onSynopsisClicked(title: String, synopsis: String) {
        AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(synopsis)
                .setPositiveButton(R.string.ok, null)
                .show()
    }

    override fun onStreamingServiceClicked(url: String) {
        streamingServiceClickListener?.onClick(url)
    }

    override fun onLengthClicked() {
        episodesClickListener?.onClick()
    }

    override fun onReleaseInfoClicked() {
        seasonClickListener?.onClick()
    }

    override fun onReviewClicked(id: String) {
        // TODO: Implementation
    }

    override fun onAllReviewsClicked() {
        reviewsClickListener?.onClick()
    }

    override fun onCharacterClicked(id: String) {
        // TODO: Implementation
    }

    override fun onAllCharactersClicked() {
        charactersClickListener?.onClick()
    }

    override fun onGenreClicked(slug: String) {
        // TODO: Implementation
    }

    override fun onRelatedClicked(id: String) {
        // TODO: Implementation
    }

    interface StreamingServiceClickListener {
        fun onClick(url: String)
    }

    interface SimpleClickListener {
        fun onClick()
    }
}