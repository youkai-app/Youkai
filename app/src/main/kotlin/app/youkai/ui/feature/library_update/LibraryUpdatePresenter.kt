package app.youkai.ui.feature.library_update

import app.youkai.data.models.Credentials
import app.youkai.data.models.LibraryEntry
import app.youkai.data.service.Api
import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LibraryUpdatePresenter : MvpBasePresenter<LibraryUpdateView>() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    // TODO: persist across changes
    private var libraryEntry: LibraryEntry = LibraryEntry()
    private var checkedForExistenceOnRemote: Boolean = false

    override fun attachView(view: LibraryUpdateView?) {
        super.attachView(view)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) subscriptions.dispose()
    }

    fun setEntry(entry: LibraryEntry) {
        libraryEntry = entry
    }

    fun setEntryId(id: String) {
        libraryEntry.id = id
    }

    fun setPrivate(isPrivate: Boolean) {
        libraryEntry.private = isPrivate
    }

    //TODO: set up statuses
    fun setStatus(status: String) {
        libraryEntry.status = status
    }

    fun setProgress(progress: Int) {
        libraryEntry.progress = progress
    }

    fun setReconsumeCount(reconsumeCount: Int) {
        libraryEntry.reconsumeCount = reconsumeCount
    }

    fun setRating(rating: Float) {
        val ratingTwenty = rating.times(4).toInt()
        if (ratingTwenty > 20) {
            throw error("Rating cannot be this big!") //TODO: handle error properly
        }
        libraryEntry.ratingTwenty = ratingTwenty
    }

    fun setNotes(notes: String) {
        libraryEntry.notes = notes
    }
    /**
     * Posts any changes to the server.
     */
    fun postUpdate() {
        System.out.println("posting update")
        val updateObservable = Observable.empty<LibraryEntry>()
                .subscribeOn(Schedulers.io())

        if (!checkedForExistenceOnRemote) {
            /**
             * If we don't konw whether or not the entry exists, find out!
             */
            updateObservable.concatMap {
                //TODO: fix for different media types, media ids and safe user access token usage
                Api.libraryEntryForAnime(Credentials().accessToken!!, "1")
                        .get()
                        .doOnNext { checkedForExistenceOnRemote = true }
                        .map { doc -> doc.get() }
                        .map { entries ->
                            run {
                                /**
                                 * [entries] will be empty (no items / null items) if an entry does not already exist
                                 * Take the id from the returned library entry but nothing else (want to keep our edits)
                                 */
                                if (!entries.isEmpty()) libraryEntry.id == entries.first().id
                                return@map libraryEntry
                            }
                        }
            }
        }

        updateObservable.concatMap { entry: LibraryEntry ->
            if (libraryEntry.id == null) {
                return@concatMap Api.createLibraryEntry(entry, Credentials().accessToken!!)
            } else {
                return@concatMap Api.updateLibraryEntry(entry, Credentials().accessToken!!)
            }
        }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            // do nothing
                        },
                        // onError
                        {
                            //TODO: error handling
                        },
                        // onComplete
                        {
                            // do nothing
                        }
                )
    }

}