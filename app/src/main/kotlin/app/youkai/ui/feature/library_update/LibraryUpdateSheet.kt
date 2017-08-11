package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import kotlinx.android.synthetic.main.library_update.*

//class LibraryUpdateSheet : MvpBottomSheetFragment<LibraryUpdateView, LibraryUpdatePresenter>(), LibraryUpdateView {
class LibraryUpdateSheet : BottomSheetDialogFragment(), LibraryUpdateView {
    var presenter: LibraryUpdatePresenter = LibraryUpdatePresenter()

    fun createPresenter() {
        presenter = LibraryUpdatePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.library_update, container, false)

        // manually calling presenter method
        presenter.attachView(this)

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()

        // manually calling presenter method
        presenter.detachView(retainInstance = false)

    }

    private fun postUpdate () {
        //TODO: handle status
        presenter.postEntryUpdate(
                privacySwitch.isChecked,
                "status",
                progressView.progress,
                rewatchedView.progress,
                ratingBar.rating,
                notesInputEdit.text.toString()
        )
    }

}