package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import app.youkai.ui.MvpBottomSheetFragment
import com.hannesdorfmann.mosby3.mvp.MvpFragment
import kotlinx.android.synthetic.main.library_update.*

class LibraryUpdateFragment : MvpFragment<LibraryUpdateView, LibraryUpdatePresenter>(), LibraryUpdateView {

    override fun createPresenter(): LibraryUpdatePresenter {
        return LibraryUpdatePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.library_update, container, false)

        System.out.println("SHASHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH")
        privacy_switch?.setOnCheckedChangeListener { button, b -> postUpdate() }
        title?.setText("DFSAOIOOOO")
        return view
    }

    private fun postUpdate () {
        System.out.println("update SODMSA")

        //TODO: handle status
        presenter.postNewEntry(
                privacy_switch.isChecked,
                "status",
                progress_view.progress,
                rewatched_view.progress,
                rating_bar.rating,
                notes_input_edit.text.toString()
        )
    }

}