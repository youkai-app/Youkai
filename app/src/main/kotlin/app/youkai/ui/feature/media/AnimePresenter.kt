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
                .fields(Category().type.type, Category.NSFW, Category.SLUG, Category.TITLE)
                .fields(StreamingLink().type.type, StreamingLink.STREAMER)
                .fields(Streamer().type.type, Streamer.SITE_NAME)
                .fields(Review().type.type, Review.RATING, Review.CONTENT, Review.USER)
                .fields(User().type.type, User.NAME, User.AVATAR)
                .fields(Producer().type.type, Producer.NAME)
                .fields(Character().type.type, Character.IMAGE)
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it.get() }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext { setMedia(it) }
                .observeOn(Schedulers.io())
                .concatMap {
                    Api.mediaRelationshipsForAnime(mediaId)
                            .include(MediaRelationship.DESTINATION)
                            .fields(MediaRelationship().type.type, MediaRelationship.ROLE, MediaRelationship.DESTINATION)
                            .fields(Anime().type.type, BaseMedia.TITLES, BaseMedia.POSTER_IMAGE)
                            .get()
                }
                .observeOn(Schedulers.computation())
                .map { it.get() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            media?.medias = it
                            setMedia(media)
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
