package app.youkai.ui.feature.library_update.view

import android.content.Context
import android.view.View
import app.youkai.R
import app.youkai.progressview.ProgressView
import app.youkai.ui.feature.library_update.AnimeLibraryUpdatePresenter
import app.youkai.ui.feature.library_update.AnimeStatusResolver
import app.youkai.ui.feature.library_update.BaseLibraryUpdatePresenter
import app.youkai.util.ext.removeAllAndAdd
import kotlinx.android.synthetic.main.library_update.view.*
import kotlinx.android.synthetic.main.library_update_progress_anime.view.*

class AnimeLibraryUpdateView(presenter: BaseLibraryUpdatePresenter, rootView: View, context: Context)
    : BaseLibraryUpdateView(presenter, rootView, context) {

    init {
        statusResolver = AnimeStatusResolver(context)
        setStatusSpinner(R.array.anime_statuses)
        setStatusSpinnerListener(statusResolver)
    }

    fun setEpisodeProgress(progress: Int) {
        if (rootView.progressContainer.findViewById<ProgressView>(R.id.episodesProgressView) != null) rootView.episodesProgressView.progress = progress
        else throw IllegalArgumentException("No episodesProgressView was inflated.")
    }

    fun setMaxEpisodes(max: Int) {
        if (rootView.progressContainer.findViewById<ProgressView>(R.id.episodesProgressView) != null) rootView.episodesProgressView.max = max
        else throw IllegalArgumentException("No episodesProgressView was inflated.")
    }

    override fun setProgressViews() {
        val layout: Int = R.layout.library_update_progress_anime
        val episodes: ProgressView? = rootView.episodesProgressView
        container.removeAllAndAdd(layoutInflater, layout)
        // set listeners
        if (presenter is AnimeLibraryUpdatePresenter) episodes?.setListener { presenter.setProgress(episodes.progress) }
    }

}