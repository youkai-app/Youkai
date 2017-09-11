package app.youkai.ui.feature.library_update.view

import android.app.AlertDialog
import android.content.Context
import android.support.annotation.ArrayRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import app.youkai.R
import app.youkai.data.models.Status
import app.youkai.ui.feature.library_update.AestheticsDelegate
import app.youkai.ui.feature.library_update.BaseLibraryUpdatePresenter
import app.youkai.ui.feature.library_update.StatusResolver
import app.youkai.util.ext.getColorCompat
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.checkedChanges
import com.jakewharton.rxbinding2.widget.itemSelections
import com.jakewharton.rxbinding2.widget.textChangeEvents
import kotlinx.android.synthetic.main.library_update.view.*

/**
 * TODO: create LibraryUpdatePresenter interface
 */
abstract class BaseLibraryUpdateView(
        protected val presenter: BaseLibraryUpdatePresenter,
        protected val rootView: View,
        protected val context: Context) : LibraryUpdateView {

    protected val layoutInflater: LayoutInflater = LayoutInflater.from(context)
    protected val aestheticsDelegate = AestheticsDelegate(rootView, context)
    /**
     * Should be initialised in init of subclassing Views.
     */
    protected lateinit var statusResolver: StatusResolver

    protected val container: ViewGroup =
            if (rootView.progressContainer != null) rootView.progressContainer
            else throw NullPointerException("No container to inflate progress views into.")

    init {
        setProgressViews()
        setViewListeners()
        aestheticsDelegate.applyLightColors()
    }

    override fun setTitle(title: String) {
        rootView.title.text = title
    }

    override fun setPrivate(isPrivate: Boolean) {
        rootView.privacySwitch.isChecked = isPrivate
        aestheticsDelegate.setPrivateBackground(isPrivate)
    }

    override fun setStatus(status: Status) {
        rootView.statusSpinner.setSelection(statusResolver.getItemPosition(status))
    }

    override fun setReconsumedCount(reconsumedCount: Int) {
        rootView.reconsumedProgressView.progress = reconsumedCount
    }

    override fun setRating(rating: Float) {
        rootView.ratingBar.rating = rating
    }

    override fun setNotes(notes: String) {
        rootView.notesInputEdit.setText(notes)
    }

    /**
     * Needs to be called in init
     */
    private fun setViewListeners() {
        rootView.privacySwitch.checkedChanges()
                .skipInitialValue()
                .subscribe { isPrivate ->
                    presenter.setPrivate(isPrivate)
                    presenter.postUpdate()
                    setPrivate(isPrivate)
                }
        //TODO: handle rating preferences
        rootView.ratingBar.setOnRatingChangeListener { _, rating ->
            presenter.setRating(rating)
            presenter.postUpdate()
        }
        rootView.notesInputEdit.textChangeEvents()
                .skipInitialValue()
                .subscribe { t ->
                    presenter.setNotes(t.text().toString())
                    presenter.postUpdate()
                }
        rootView.removeButton.clicks()
                .subscribe { _ ->
                    showRemovalConfirmationDialog()
                    presenter.postUpdate()
                }
    }

    abstract protected fun setProgressViews()

    /**
     * Should be called in init of subclassing Views.
     */
    protected fun setStatusSpinnerListener(statusResolver: StatusResolver) {
        rootView.statusSpinner.itemSelections()
                .skipInitialValue()
                .doOnNext {
                    if (rootView.privacySwitch.isChecked) aestheticsDelegate.setSpinnerSelectedDark()
                    else aestheticsDelegate.setSpinnerSelectedLight()
                }
                .filter { rootView.statusSpinner.adapter != null }
                .map { rootView.statusSpinner.adapter.getItem(it).toString() }
                .map { statusResolver.getItemStatus(it) }
                // filter out any updates that aren't any different from the current status
                .filter {
                    if (presenter.libraryEntry.status != null) {
                        it != statusResolver.getItemStatus(presenter.libraryEntry.status!!)
                    }
                    // if the status hasn't been set in the presenter, we want to handle the change
                    else true
                }
                .doOnNext {
                    presenter.setStatus(it)
                    presenter.postUpdate()
                }
                .subscribe()
    }

    protected fun setStatusSpinner(@ArrayRes arrayRes: Int) {
        val statusAdapter = ArrayAdapter.createFromResource(context, arrayRes, R.layout.simple_spinner_item)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        rootView.statusSpinner.adapter = statusAdapter
    }

    private fun showRemovalConfirmationDialog() {
        val dialog = AlertDialog.Builder(context, R.style.LibraryUpdateDialog)
                .setTitle(R.string.delete_confirmation_title)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(
                        R.string.delete_confirmation_positive,
                        { _, _ ->
                            presenter.removeLibraryEntry()
                        }
                )
                .setNeutralButton(
                        android.R.string.cancel,
                        { _, _ ->
                            //do nothing
                        }
                )
                .create()
        dialog.show()
        dialog.getButton(android.app.AlertDialog.BUTTON_NEUTRAL)
                .setTextColor(context.getColorCompat(R.color.text_gray_light))
    }

}