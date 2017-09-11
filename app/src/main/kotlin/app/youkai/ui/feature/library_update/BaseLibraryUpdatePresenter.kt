package app.youkai.ui.feature.library_update

import app.youkai.data.models.*
import app.youkai.data.models.ext.MediaType
import app.youkai.data.service.Api
import app.youkai.ui.feature.library_update.view.AnimeLibraryUpdateView
import app.youkai.ui.feature.library_update.view.LibraryUpdateViewManager
import app.youkai.ui.feature.library_update.view.MangaLibraryUpdateView
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseLibraryUpdatePresenter() : MvpBasePresenter<LibraryUpdateViewManager>() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    // TODO: persist across changes
    var libraryEntry: LibraryEntry = LibraryEntry()
    private var checkedForExistenceOnRemote: Boolean = false

    override fun attachView(viewManager: LibraryUpdateViewManager?) {
        super.attachView(viewManager)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) subscriptions.dispose()
    }

    /**
     * "renaming" [view] to [viewManager] in an attempt to make the code in here easier to read.
     */
    protected fun getViewManager() = view

    /**
     * Must have a media item set (anime or manga) by including it in the call
     */
     protected fun setEntry(entryStream: Observable<LibraryEntry>) {
        entryStream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            libraryEntry = it
                            this.setEntryOnView(it)
                        },
                        // onError
                        {
                            it.printStackTrace()
                            //TODO: error handling
                        },
                        // onComplete
                        {
                            // do nothing
                        }
                )
    }

    private fun setEntryOnView(entry: LibraryEntry) {
        if (entry.anime != null) {
            getViewManager()?.setMediaType(MediaType.ANIME)
            (getViewManager()?.getViewT() as AnimeLibraryUpdateView).apply {
                setMaxEpisodes(entry.anime!!.episodeCount!!)
                setEpisodeProgress(entry.progress!!)
            }
            setViewTitles(entry.anime!!.titles!!)
        } else if (entry.manga != null) {
            getViewManager()?.setMediaType(MediaType.MANGA)
            (getViewManager()?.getViewT() as MangaLibraryUpdateView).apply {
                setTitle(entry.manga!!.titles!!.en!!)
                setMaxChapters(entry.manga!!.chapterCount!!)
                setChapterProgress(entry.progress!!)
                setMaxVolumes(entry.manga!!.volumeCount!!)
                setVolumeProgress(entry.volumesOwned!!)
            }
        } else throw IllegalArgumentException("No related media.")
        libraryEntry = entry
        //TODO: make safe!! (!!)
        getViewManager()?.getViewT()?.apply {
            setPrivate(entry.private!!)
            setStatus(Status(entry.status!!))
            setReconsumedCount(entry.reconsumeCount!!)
            //TODO: create dedicated rating model or methods?
            if (entry.ratingTwenty != null && entry.ratingTwenty!! >= 0)
                setRating(entry.ratingTwenty!!.div(4).toFloat())
            if (entry.notes != null)
                setNotes(entry.notes!!)
        }
    }

    fun getEntryById(entryId: String) = setEntry(
            Api.libraryEntry(entryId)
                    .include("anime", "manga")
                    .fields("anime", Anime.EPISODE_COUNT, BaseMedia.TITLES)
                    .fields("manga", BaseMedia.TITLES, Manga.CHAPTERS_COUNT, Manga.VOLUME_COUNT)
                    .get()
                    .observeOn(Schedulers.computation())
                    .map { it.get() }
    )

    protected fun setViewTitles(titles: Titles) {
        //TODO: title preferences
        getViewManager()?.getViewT()?.setTitle(titles.en ?: titles.enJp ?: titles.jaJp ?: throw IllegalArgumentException("No available title."))
    }

    fun setPrivate(isPrivate: Boolean) {
        libraryEntry.private = isPrivate
    }

    //TODO: change library entry to use Status
    fun setStatus(status: Status) {
        libraryEntry.status = status.value
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
        if (!isAuthed()) {
            sendToLogin()
            return
        }

        val updateObservable = Observable.just(libraryEntry)
                .subscribeOn(Schedulers.io())

        if (libraryEntry.id == null && !checkedForExistenceOnRemote) {
            /**
             * If we don't know whether or not the entry exists, find out!
             */
            updateObservable.concatMap {
                //TODO: global user object
                val libraryEntryRequest =
                        if (libraryEntry.anime != null) Api.libraryEntryForAnime("userid", libraryEntry.anime!!.id!!)
                        else if (libraryEntry.manga != null) Api.libraryEntryForManga("userid", libraryEntry.manga!!.id!!)
                        else throw IllegalArgumentException("Cannot retrieve library entry without a library entry id, anime id or manga id.")
                libraryEntryRequest
                        .get()
                        .doOnNext { checkedForExistenceOnRemote = true }
                        .observeOn(Schedulers.computation())
                        .map { it.get() }
                        .map { entries ->
                            /**
                             * [entries] will be empty (no items / null items) if an entry does not already exist
                             * Take the id from the returned library entry but nothing else (want to keep our edits)
                             */
                            if (!entries.isEmpty()) libraryEntry.id = entries.first().id
                            libraryEntry
                        }
            }
        }

        updateObservable
                .observeOn(Schedulers.io())
                .concatMap { entry: LibraryEntry ->
                    // if the entry existed, the id would have been set by this point
                    if (libraryEntry.id == null) {
                        Api.createLibraryEntry(entry, Credentials().accessToken!!)
                    } else {
                        Api.updateLibraryEntry(entry, Credentials().accessToken!!)
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
                            it.printStackTrace()
                            //TODO: error handling
                        },
                        // onComplete
                        {
                            // do nothing
                        }
                )
    }

    fun removeLibraryEntry() {
        if (!isAuthed()) {
            sendToLogin()
            return
        }

        Api.deleteLibraryEntry(libraryEntry.id!!, Credentials().accessToken!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            // do nothing
                        },
                        // onError
                        {
                            it.printStackTrace()
                            //TODO: error handling
                        },
                        // onComplete
                        {
                            // do nothing
                        }
                )
    }

    protected fun isAuthed(): Boolean = Credentials().accessToken != null

    private fun sendToLogin() {
        view?.sendToLogin()
    }

}

