package app.youkai.ui.feature.media.characters

import app.youkai.R
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import app.youkai.data.models.ext.MediaType
import app.youkai.data.service.Api
import app.youkai.util.ext.whenNotNull
import app.youkai.util.string
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersPresenter : MvpBasePresenter<CharactersView>() {
    private var mediaId: String? = null
    private var mediaType: MediaType? = null

    private var currentLanguage: String? = null
    private var currentPage = 1

    private var loading = false
    private var loadedAll = false

    fun set(mediaId: String, mediaType: MediaType, mediaTitle: String) {
        this.mediaId = mediaId
        this.mediaType = mediaType
        setMediaTitle(mediaTitle)
    }

    fun loadNextPage() {
        if (!loading && !loadedAll) {
            load(page = ++currentPage, showLoading = false)
        }
    }

    private fun setMediaTitle(title: String) {
        view?.setMediaTitle(title)
    }

    fun load(mediaId: String? = this.mediaId,
             mediaType: MediaType? = this.mediaType,
             language: String? = currentLanguage,
             page: Int = currentPage,
             showLoading: Boolean = true
    ) {
        if (mediaId == null || mediaType == null) {
            throw IllegalStateException("mediaId and mediaType cannot be null. Are you sure you " +
                    "are calling set() before load()?")
        }

        currentPage = page

        if (language != null && language != currentLanguage) {
            currentLanguage = language
            currentPage = 1
        }

        if (showLoading) {
            view?.switchToLoading()
        }
        loading = true

        when (mediaType) {
            MediaType.ANIME -> {
                val request = Api.castingsForAnime(mediaId)
                        .filter("isCharacter", "true")
                        .include("character", "person")
                        .sort("-featured")
                        .page("limit", 10)
                        .page("offset", 10 * (currentPage - 1)) // if on the first page, offset is 0

                whenNotNull(language) {
                    request.filter("language", it)
                }

                request.get()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { m ->
                                    whenNotNull(m.get()) {
                                        if (it.isEmpty()) loadedAll = true
                                        view?.setCharacters(it, currentPage > 1)
                                        view?.switchToContent()
                                    }
                                },
                                { e ->
                                    onError(e)
                                },
                                {
                                    loading = false
                                }
                        )
            }
            MediaType.MANGA -> {
                // Nothing right now
            }
        }
    }

    fun onError(e: Throwable) {
        view?.setError(string(R.string.error_view_oops), e.message ?: "", showRetryButton = true)
        view?.switchToError()
    }

    fun onScrollEndReached() {
        view?.showBottomProgressBar()
        loadNextPage()
    }
}