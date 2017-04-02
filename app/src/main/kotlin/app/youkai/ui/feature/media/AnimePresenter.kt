package app.youkai.ui.feature.media

import android.util.Log
import app.youkai.App.Companion.context
import app.youkai.R
import app.youkai.data.models.Anime
import app.youkai.data.service.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Top-level media presenter that takes care of anime-specific functions.
 */
class AnimePresenter : BaseMediaPresenter() {

    override fun loadMedia(mediaId: String) {
        Api.anime(mediaId)
                .include(
                        "genres",
                        "streamingLinks.streamer",
                        "reviews.user",
                        "animeProductions.producer",
                        "animeCharacters.character"
//                        "mediaRelationships.destination"
                )
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        { m ->
                            run {
                                if (m != null) {
                                    setMedia(m)
                                }
                            }
                        },
                        // onError
                        { e ->
                            throw e
                            // TODO: Handle
                        },
                        // onComplete
                        {
                            Log.wtf("CCCCCCC", "")
                        }
                )
    }

    override fun setType() {
        view?.setType(
                when ((media as Anime?)?.showType) {
                    "TV" -> context.getString(R.string.media_type_anime_tv)
                    "Special" -> context.getString(R.string.media_type_anime_special)
                    "OVA" -> context.getString(R.string.media_type_anime_ova)
                    "ONA" -> context.getString(R.string.media_type_anime_ona)
                    "Movie" -> context.getString(R.string.media_type_anime_movie)
                    "Music" -> context.getString(R.string.media_type_anime_music)
                    else -> "?"
                }
        )
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
