package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import app.youkai.R
import com.jakewharton.rxbinding2.widget.RxTextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.library_update.view.*
import java.util.*

//class LibraryUpdateSheet : MvpBottomSheetFragment<LibraryUpdateView, LibraryUpdatePresenter>(), LibraryUpdateView {
class LibraryUpdateSheet : BottomSheetDialogFragment(), LibraryUpdateView {
    var presenter: LibraryUpdatePresenter = LibraryUpdatePresenter()

    fun createPresenter() {
        presenter = LibraryUpdatePresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.library_update, container, false)
        // manually calling presenter method
        presenter.attachView(this)

        v.privacySwitch.setOnCheckedChangeListener { _, isPrivate -> presenter.setPrivate(isPrivate) }
        //TODO: make MangaStatusResolver
        val statusAdapter = ArrayAdapter.createFromResource(context, R.array.anime_statuses, R.layout.simple_spinner_item)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        v.statusSpinner.adapter = statusAdapter
        v.statusSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
                // do nothing?
            }

            override fun onItemSelected(parentView: AdapterView<*>?, selectedItemView: View?, position: Int, id: Long) {
                //TODO: make MangaStatusResolver
                System.out.println(AnimeStatusResolver.getItemStatus(statusAdapter.getItem(position).toString()))
            }

        }
        v.progressView.setListener { presenter.setProgress(v.progressView.progress) }
        //TODO: set max properly
        v.progressView.max = 12
        v.rewatchedView.setListener { presenter.setProgress(v.progressView.progress) }
        v.ratingBar.setOnRatingChangeListener { _, rating -> presenter.setRating(rating) }
        RxTextView.textChangeEvents(v.notesInputEdit)
                .skipInitialValue()
                .subscribe { t -> presenter.setNotes(t.text().toString()) }

        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // manually calling presenter method
        presenter.detachView(retainInstance = false)
    }

    override fun onPause() {
        /**
         * Save when:
         *  - dismissed
         *  - screen off
         *  - switched app
         *  -
         */
        presenter.postUpdate()

        super.onPause()
    }

}