package app.youkai.ui.feature.library_update

import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Manga
import app.youkai.data.service.Api
import app.youkai.ui.feature.library_update.view.MangaLibraryUpdateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MangaLibraryUpdatePresenter : BaseLibraryUpdatePresenter() {

    //TODO: global user object
    fun getEntryByManga(mangaId: String) {
        /**
         * Sets title, no. of chapters and no. of volumes so that they can be set in the case that there
         * exists no library entry for this particular manga without needing to wait for
         * the library entry call to finish.
         */
        loadBasics(mangaId)

        setEntry(
                Api.libraryEntryForManga(userId = "157458", mangaId = mangaId)
                        .include("manga")
                        .fields("manga", BaseMedia.TITLES, Manga.CHAPTERS_COUNT, Manga.VOLUME_COUNT)
                        .get()
                        .observeOn(Schedulers.computation())
                        .map { it.get() }
                        .flatMapIterable { l -> l }
                        .take(1)
        )
    }


    /**
     * Fetches and displays the titles, no. of chapters and no. of volumes.
     */
    fun loadBasics(mangaId: String) {
        Api.manga(mangaId)
                .fields(Manga().type.type, BaseMedia.TITLES, Manga.CHAPTERS_COUNT, Manga.VOLUME_COUNT)
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it.get() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            setViewTitles(it.titles!!)
                            getViewT().setMaxChapters(it.chapterCount!!)
                            getViewT().setMaxVolumes(it.volumeCount!!)
                        },
                        // onError
                        {
                            it.printStackTrace()
                            //TODO: error handling
                        },
                        // onComplete
                        {}
                )
    }

    fun setVolumesProgress(progress: Int) {
        libraryEntry.volumesOwned = progress
    }

    //TODO: add this to presenter interface
    private fun getViewT() = (getViewManager()?.getViewT() as MangaLibraryUpdateView)

}