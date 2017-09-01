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
                .include(BaseMedia.CATEGORIES)
                .includeNested(BaseMedia.CASTINGS, Casting.PERSON)
                .includeNested(BaseMedia.REVIEWS, Review.USER)
                .includeNested(BaseMedia.MEDIA_RELATIONSHIPS, MediaRelationship.DESTINATION)
                .fields(Category().type.type, Category.NSFW, Category.SLUG, Category.TITLE)
                .fields(Review().type.type, Review.RATING, Review.CONTENT, Review.USER)
                .fields(User().type.type, User.NAME, User.AVATAR)
                .fields(Producer().type.type, Producer.NAME)
                .fields(Character().type.type, Character.IMAGE)
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