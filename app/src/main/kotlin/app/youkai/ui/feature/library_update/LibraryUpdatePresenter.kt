package app.youkai.ui.feature.library_update

import app.youkai.data.service.Api
import com.hannesdorfmann.mosby3.mvp.MvpPresenter

class LibraryUpdatePresenter : MvpPresenter<LibraryUpdateView> {

    override fun attachView(view: LibraryUpdateView?) {

    }

    override fun detachView(retainInstance: Boolean) {

    }

    //TODO: set up statuses
    fun postEntryUpdate (isPrivate: Boolean,
                      status: String,
                      progress: Int,
                      rewatched: Int,
                      rating: Float,
                      notes: String) {

    }

}