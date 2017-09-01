package app.youkai.ui.feature.media

import app.youkai.App.Companion.context
import app.youkai.R
import app.youkai.data.models.*
import app.youkai.data.models.ext.typeString
import app.youkai.data.service.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Top-level media presenter that takes care of anime-specific functions.
 */
class AnimePresenter : BaseMediaPresenter() {

    override fun loadMedia(mediaId: String) {
        Api.anime(mediaId)
                .include(BaseMedia.CATEGORIES)
                .includeNested(Anime.STREAMING_LINKS, StreamingLink.STREAMER)
                .includeNested(BaseMedia.REVIEWS, Review.USER)
                .includeNested(Anime.PRODUCTIONS, AnimeProduction.PRODUCER)
                .includeNested(Anime.CHARACTERS, AnimeCharacter.CHARACTER)
                .includeNested(BaseMedia.MEDIA_RELATIONSHIPS, MediaRelationship.DESTINATION)
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { m ->
                            run {
                                setMedia(m.get())
                            }
                        },
                        { e ->
                            setMedia(null)
                        },
                        {
                            // onComplete
                        }
                )
    }

    override fun setMedia(media: BaseMedia?) {
        super.setMedia(media)
        view?.setTrailerButtonVisible(media is Anime && media.youtubeVideoId != null)
    }

    override fun setType() {
        view?.setType((media as Anime?)?.typeString() ?: "")
    }

    override fun onTrailerClicked() {
        val url = (media as Anime?)?.youtubeVideoId
        if (url != null) {
            view?.showTrailer(url)
        } else {
            view?.showToast(context.getString(R.string.error_no_trailer))
        }
    }
}
