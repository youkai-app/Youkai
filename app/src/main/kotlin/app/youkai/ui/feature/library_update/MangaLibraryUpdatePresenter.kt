package app.youkai.ui.feature.library_update

import app.youkai.data.models.BaseMedia
import app.youkai.data.service.Api
import io.reactivex.schedulers.Schedulers

class MangaLibraryUpdatePresenter : BaseLibraryUpdatePresenter() {

    //TODO: global user object
    fun getEntryByManga(mangaId: String) = setEntry(
            Api.libraryEntryForManga(userId = "157458", mangaId = mangaId)
                    .include("manga")
                    //.fields("manga", Manga.CHAPTERS_COUNT)
                    .fields("manga", BaseMedia.TITLES)
                    .get()
                    .observeOn(Schedulers.computation())
                    .map { doc -> doc.get() }
                    .flatMapIterable { l -> l }
    )

    fun setVolumesProgress(progress: Int) {
        libraryEntry.volumesOwned = progress
    }

}