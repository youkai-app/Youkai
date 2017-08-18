package app.youkai.ui.feature.library_update

import app.youkai.data.models.JsonType
import app.youkai.data.models.Status
import com.hannesdorfmann.mosby3.mvp.MvpView

interface LibraryUpdateView : MvpView {

    fun setMediaType(mediaType: JsonType)

    fun setTitle(title: String)

    fun setPrivate(isPrivate: Boolean)

    fun setStatus(status: Status)

    fun setEpisodeProgress(progress: Int)

    fun setChapterProgress(progress: Int)

    fun setVolumeProgress(progress: Int)

    fun setMaxEpisodes(max: Int)

    fun setMaxChapters(max: Int)

    fun setMaxVolumes(max: Int)

    fun setReconsumedCount(reconsumedCount: Int)

    /**
     * Float between 0 and 5
     */
    fun setRating(rating: Float)

    fun setNotes(notes: String)

}