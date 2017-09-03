package app.youkai.ui.feature.library_update.view

import app.youkai.data.models.Status
import com.hannesdorfmann.mosby.mvp.MvpView

interface LibraryUpdateView : MvpView {

    fun setTitle(title: String)

    fun setPrivate(isPrivate: Boolean)

    fun setStatus(status: Status)

    fun setReconsumedCount(reconsumedCount: Int)

    /**
     * Float between 0 and 5
     */
    fun setRating(rating: Float)

    fun setNotes(notes: String)

}