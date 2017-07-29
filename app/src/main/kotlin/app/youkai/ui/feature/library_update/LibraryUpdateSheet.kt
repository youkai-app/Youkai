package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import app.youkai.ui.MvpBottomSheetFragment

class LibraryUpdateSheet : MvpBottomSheetFragment<LibraryUpdateView, LibraryUpdatePresenter>(), LibraryUpdateView {
    override fun createPresenter(): LibraryUpdatePresenter {
        return LibraryUpdatePresenter()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = super.onCreateView(inflater, container, savedInstanceState)
        inflater.inflate(R.layout.library_update, container, false)
        super.onViewCreated(v, savedInstanceState)
        return v
    }

    /*
    init {
        setContentView(R.layout.library_update_dialog)
        setCanceledOnTouchOutside(true)
    }*/

    override fun onStart() {
        super.onStart()
    }

}