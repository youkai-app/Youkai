package app.youkai.ui.feature.library_update

import app.youkai.data.models.Anime
import app.youkai.data.models.BaseMedia
import app.youkai.data.service.Api
import app.youkai.ui.feature.library_update.view.AnimeLibraryUpdateView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AnimeLibraryUpdatePresenter : BaseLibraryUpdatePresenter() {

    //TODO: global user object
    fun getEntryByAnime(animeId: String) {
        /**
         * Sets title and no. of episodes so that they can be set in the case that there
         * exists no library entry for this particular anime without needing to wait for
         * the library entry call to finish.
         */
        loadBasics(animeId)

        setEntry(
                Api.libraryEntryForAnime(userId = "157458", animeId = animeId)
                        //TODO: fix ugly JsonType implementation
                        .include("anime")
                        .fields("anime", Anime.EPISODE_COUNT, BaseMedia.TITLES)
                        .get()
                        .observeOn(Schedulers.computation())
                        .map { it.get() }
                        .flatMapIterable { l -> l }
                        .take(1)
        )
    }

    /**
     * Fetches and displays the title and no. of episodes.
     */
    private fun loadBasics(animeId: String) {
        Api.anime(animeId)
                .fields(Anime().type.type, BaseMedia.TITLES, Anime.EPISODE_COUNT)
                .get()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.computation())
                .map { it.get() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        // onNext
                        {
                            setViewTitles(it.titles!!)
                            getViewT().setMaxEpisodes(it.episodeCount!!)
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

    private fun getViewT() = (getViewManager()?.getViewT() as AnimeLibraryUpdateView)

}