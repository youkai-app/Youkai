package app.youkai.ui.feature.media

import app.youkai.data.models.Manga
import app.youkai.data.models.ext.typeString
import app.youkai.data.service.Api
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Top-level media presenter that takes care of manga-specific functions.
 */
class MangaPresenter : BaseMediaPresenter() {

    override fun loadMedia(mediaId: String) {
        onLoading()
        Api.manga(mediaId)
                .include(
                        "genres",
                        "castings.person",
                        /*"mediaRelationships.destination",*/
                        "reviews.user"
                )
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { m ->
                            run {
                                setMedia(m.get())
                                onContent()
                            }
                        },
                        { e ->
                            onError(e)
                        },
                        {
                            // onComplete
                        }
                )
    }

    override fun setType() {
        view?.setType((media as Manga?)?.typeString() ?: "")
    }

    override fun onError(e: Throwable) {
        view?.tellChildrenError(e)
    }
}