package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import com.hannesdorfmann.mosby.mvp.MvpFragment

class LibraryUpdateFragment : MvpFragment<LibraryUpdateView, LibraryUpdatePresenter>(), LibraryUpdateView {

    override fun createPresenter(): LibraryUpdatePresenter {
        return LibraryUpdatePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.library_update, container, false)
    }

}