package app.youkai.ui.feature.library_update

import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState

class LibraryUpdateState : RestorableViewState<LibraryUpdateView> {
    val isPrivate = false
    // TODO: handle status
    val status = null
    val progress = 0
    val rewatched = 0
    val rating: Float = 0f
    val originalNotes = null
    val editedNotes = null
    val saveVisible = false

    override fun restoreInstanceState(`in`: Bundle?): RestorableViewState<LibraryUpdateView> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveInstanceState(out: Bundle) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun apply(view: LibraryUpdateView?, retained: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}