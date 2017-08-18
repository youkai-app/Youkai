package app.youkai.ui.feature.library_update

import app.youkai.data.models.Anime
import app.youkai.data.models.BaseMedia
import app.youkai.data.service.Api
import io.reactivex.schedulers.Schedulers

class AnimeLibraryUpdatePresenter : BaseLibraryUpdatePresenter() {

    //TODO: global user object
    fun getEntryByAnime(animeId: String) = setEntry(
            Api.libraryEntryForAnime(userId = "157458", animeId = animeId)
                    //TODO: fix ugly JsonType implementation
                    .include("anime")
                    .fields("anime", Anime.EPISODE_COUNT, BaseMedia.TITLES)
                    .get()
                    .observeOn(Schedulers.computation())
                    .map { doc -> doc.get() }
                    .flatMapIterable { l -> l }
    )

}