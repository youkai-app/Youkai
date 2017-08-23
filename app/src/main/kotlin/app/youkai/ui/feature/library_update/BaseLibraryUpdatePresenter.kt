package app.youkai.ui.feature.library_update

import app.youkai.data.models.*
import app.youkai.data.service.Api
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class BaseLibraryUpdatePresenter : MvpBasePresenter<LibraryUpdateView>() {

    private val subscriptions: CompositeDisposable = CompositeDisposable()
    // TODO: persist across changes
    var libraryEntry: LibraryEntry = LibraryEntry()
    private var checkedForExistenceOnRemote: Boolean = false

    override fun attachView(view: LibraryUpdateView?) {
        super.attachView(view)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) subscriptions.dispose()
    }

    /**
     * Must have a media item set (anime or manga) by including it in the call
     */
     protected fun setEntry(entryStream: Observable<LibraryEntry>) {
        entryStream
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        { this.setEntryOnView(it) },
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
            view.setMediaType(JsonType(Anime().type.type))
            view.setMaxEpisodes(entry.anime!!.episodeCount!!)
            //TODO: title preferences
            view.setTitle(entry.anime!!.titles!!.en!!)
            view.setEpisodeProgress(entry.progress!!)
        } else if (entry.manga != null) {
            view.setMediaType(JsonType(Manga().type.type))
            //TODO: manga doesn't have chapters count any more
            //view.setMaxChapters(entry.manga!!.chapterCount!!)
            view.setTitle(entry.manga!!.titles!!.en!!)
            view.setMaxChapters(entry.manga!!.chapterCount!!)
            view.setChapterProgress(entry.progress!!)
            view.setMaxVolumes(entry.manga!!.volumeCount!!)
            view.setVolumeProgress(entry.volumesOwned!!)
        }
        libraryEntry = entry
        //TODO: make safe!! (!!)
        view.setPrivate(entry.private!!)
        view.setStatus(Status(entry.status!!))
        view.setReconsumedCount(entry.reconsumeCount!!)
        //TODO: create dedicated rating model or methods?
        if (entry.ratingTwenty != null && entry.ratingTwenty!! >= 0) {
            view.setRating(entry.ratingTwenty!!.div(4).toFloat())
        }
        if (entry.notes != null) {
            view.setNotes(entry.notes!!)
        }
    }

    fun getEntryById(entryId: String) = setEntry(
            Api.libraryEntry(entryId)
                    .include("anime", "manga")
                    .fields("anime", Anime.EPISODE_COUNT, BaseMedia.TITLES)
                    //.fields("manga", Manga.CHAPTERS_COUNT)
                    .fields("manga", BaseMedia.TITLES, Manga.CHAPTERS_COUNT, Manga.VOLUME_COUNT)
                    .get()
                    .observeOn(Schedulers.computation())
                    .map { it.get() }
    )

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
        val updateObservable = Observable.just(libraryEntry)
                .subscribeOn(Schedulers.io())

        if (libraryEntry.id == null && !checkedForExistenceOnRemote) {
            /**
             * If we don't know whether or not the entry exists, find out!
             */
            updateObservable.concatMap {
                //TODO: fix for different media types, media ids and safe user access token usage
                Api.libraryEntryForAnime(Credentials().accessToken!!, "1")
                        .get()
                        .doOnNext {         System.out.println("posting update1") }
                        .doOnNext { checkedForExistenceOnRemote = true }
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
                .doOnNext {         System.out.println("posting update") }
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
                            System.out.println("posting update2")
                            // do nothing
                        },
                        // onError
                        {
                            it.printStackTrace()
                            //TODO: error handling
                        },
                        // onComplete
                        {
                            System.out.println("posting update3")
                            // do nothing
                        }
                )
    }

}