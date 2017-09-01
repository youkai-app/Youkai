package app.youkai.ui.feature.media

import app.youkai.data.models.*
import app.youkai.data.models.ext.typeString
import app.youkai.data.service.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Top-level media presenter that takes care of manga-specific functions.
 */
class MangaPresenter : BaseMediaPresenter() {

    override fun loadMedia(mediaId: String) {
        Api.manga(mediaId)
                .include(
                        BaseMedia.CATEGORIES,
                        "castings.person",
                        "mediaRelationships.destination",
                        "reviews.user"
                )
                .includeNested(BaseMedia.CASTINGS, Casting.PERSON)
                .includeNested(BaseMedia.REVIEWS, Review.USER)
                .include(BaseMedia.MEDIA_RELATIONSHIPS, MediaRelationship.DESTINATION)
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
                        },
                        {
                            // onComplete
                        }
                )
    }

    override fun setType() {
        view?.setType((media as Manga?)?.typeString() ?: "")
    }
}