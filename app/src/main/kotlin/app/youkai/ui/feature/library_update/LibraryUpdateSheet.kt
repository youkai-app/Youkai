package app.youkai.ui.feature.library_update

import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import app.youkai.R
import app.youkai.data.models.Anime
import app.youkai.data.models.JsonType
import app.youkai.data.models.Manga
import app.youkai.data.models.Status
import app.youkai.progressview.ProgressView
import app.youkai.util.ext.removeAllAndAdd
import kotlinx.android.synthetic.main.library_update.*
import kotlinx.android.synthetic.main.library_update.view.*
import kotlinx.android.synthetic.main.library_update_progress_anime.*
import kotlinx.android.synthetic.main.library_update_progress_anime.view.*
import kotlinx.android.synthetic.main.library_update_progress_manga.*
import kotlinx.android.synthetic.main.library_update_progress_manga.view.*
import android.view.ViewAnimationUtils
import android.animation.Animator
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.os.Build
import com.jakewharton.rxbinding2.view.clicks
import com.jakewharton.rxbinding2.widget.itemSelections
import com.jakewharton.rxbinding2.widget.textChangeEvents

/**
 * Was intended to be
 * class LibraryUpdateSheet : MvpBottomSheetFragment<LibraryUpdateView, BaseLibraryUpdatePresenter>(), LibraryUpdateView
 * but could not get MvpBottomSheetFragment to work.
 */
class LibraryUpdateSheet : BottomSheetDialogFragment(), LibraryUpdateView {

    companion object {
        val ARGUMENT_LIBRARY_ENTRY_ID = "libraryEntryId"
        val ARGUMENT_ANIME_ID = "animeId"
        val ARGUMENT_MANGA_ID = "mangaId"
    }

    private var mediaType: JsonType? = null
    private var presenter: BaseLibraryUpdatePresenter? = null
    private var statusResolver: StatusResolver? = null

    private var privacySwitchTouchX: Float? = null
    private var privacySwitchTouchY: Float? = null

    private var removeButtonIsPrimed: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments.containsKey(ARGUMENT_LIBRARY_ENTRY_ID)) {
            presenter = BaseLibraryUpdatePresenter()
            presenter!!.getEntryById(arguments.getString(ARGUMENT_LIBRARY_ENTRY_ID))
        } else if (arguments.containsKey(ARGUMENT_ANIME_ID)) {
            //TODO: mediaType
            setMediaType(JsonType("anime"))
            (presenter as AnimeLibraryUpdatePresenter).getEntryByAnime(arguments.getString(ARGUMENT_ANIME_ID))
        } else if (arguments.containsKey(ARGUMENT_MANGA_ID)) {
            setMediaType(JsonType("manga"))
            (presenter as MangaLibraryUpdatePresenter).getEntryByManga(arguments.getString(ARGUMENT_MANGA_ID))

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.library_update, container, false)
        // manually calling presenter method
        if (presenter == null) presenter = BaseLibraryUpdatePresenter()

        presenter!!.attachView(this)

        v.privacySwitch.setOnTouchListener { _, motionEvent ->
            privacySwitchTouchX = motionEvent.x
            privacySwitchTouchY = motionEvent.y
            false
        }
        v.privacySwitch.setOnCheckedChangeListener { _, isPrivate ->
            presenter!!.setPrivate(isPrivate); setPrivateBackground(isPrivate)
        }
        v.statusSpinner.itemSelections()
                .skipInitialValue()
                .filter { v.statusSpinner.adapter != null }
                .map { v.statusSpinner.adapter.getItem(it).toString() }
                .doOnNext { if (statusResolver == null) throw IllegalStateException("Cannot set a status without a statusResolver") }
                .map { statusResolver!!.getItemStatus(it) }
                .filter { presenter != null }
                .doOnNext { presenter!!.setStatus(it) }
                .subscribe()
        //TODO: handle rating preferences
        v.ratingBar.setOnRatingChangeListener { _, rating -> presenter!!.setRating(rating) }
        v.notesInputEdit.textChangeEvents()
                .skipInitialValue()
                .subscribe { t -> presenter!!.setNotes(t.text().toString()) }
        v.removeButton.clicks().subscribe{ _ -> showRemovalConfirmationDialog() }
        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // manually calling presenter method
        presenter?.detachView(retainInstance = false)
    }

    override fun onPause() {
        /**
         * Save when:
         *  - dismissed
         *  - screen off
         *  - switched app
         *  -
         */
        presenter?.postUpdate()

        super.onPause()
    }

    private fun mediaTypeIsAnime(mediaType: JsonType? = null): Boolean {
        if (mediaType != null) return mediaType.type == Anime().type.type
        else return this.mediaType!!.type == Anime().type.type
    }

    private fun mediaTypeIsManga(mediaType: JsonType? = null): Boolean {
        if (mediaType != null) return mediaType.type == Manga().type.type
        else return this.mediaType!!.type == Manga().type.type
    }

    override fun setMediaType(mediaType: JsonType) {
        if (!mediaTypeIsAnime(mediaType) && !mediaTypeIsManga(mediaType))
            throw IllegalArgumentException("Media type " + mediaType.type + " is unrecognised.")

        this.mediaType = mediaType
        configureForMediaType()
    }

    private fun configureForMediaType() {
        replacePresenterForMediaType()
        if (mediaTypeIsAnime()) {
            //TODO: set headers in view
            statusResolver = AnimeStatusResolver()
            statusResolver!!.init(context)
        } else if (mediaTypeIsManga()) {
            statusResolver = MangaStatusResolver()
            statusResolver!!.init(context)
        }
        configureViewForMediaType()
    }

    private fun configureViewForMediaType(v: View? = null) {
        setProgressViewForMediaType(v)
        setStatusSpinnerForMediaType(v)
    }

    private fun replacePresenterForMediaType() {
        val newPresenter: BaseLibraryUpdatePresenter =
                if (mediaTypeIsAnime()) AnimeLibraryUpdatePresenter()
                else if (mediaTypeIsManga()) MangaLibraryUpdatePresenter()
                else throw IllegalArgumentException("No library entry presenter for type: " + mediaType!!.type)

        if (presenter != null) newPresenter.libraryEntry = presenter!!.libraryEntry
        presenter = newPresenter
    }

    private fun setProgressViewForMediaType(v: View? = null) {
        val layout: Int =
                if (mediaTypeIsAnime())
                    R.layout.library_update_progress_anime
                else if (mediaTypeIsManga())
                    R.layout.library_update_progress_manga
                else throw IllegalArgumentException("No progress layout for media type: " + mediaType!!.type)

        val container: ViewGroup =
                if (v != null) v.progressContainer
                else if (progressContainer != null) progressContainer
                else throw NullPointerException("No container to inflate progress views into.")

        val episodes: ProgressView? =
                if (v != null) v.episodesProgressView
                else if (episodesProgressView != null) episodesProgressView
                else null

        val chapters: ProgressView? =
                if (v != null) v.chaptersProgressView
                else if (chaptersProgressView != null) chaptersProgressView
                else null

        val volumes: ProgressView? =
                if (v != null) v.volumesProgressView
                else if (volumesProgressView != null) volumesProgressView
                else null

        container.removeAllAndAdd(activity.layoutInflater, layout)

        // set listeners
        if (presenter is AnimeLibraryUpdatePresenter) {
            episodes?.setListener { (presenter as AnimeLibraryUpdatePresenter).setProgress(episodes.progress) }
        } else if (presenter is MangaLibraryUpdatePresenter) {
            chapters?.setListener { presenter!!.setProgress(chapters.progress) }
            volumes?.setListener { (presenter as MangaLibraryUpdatePresenter).setVolumesProgress(volumes.progress) }
        } else throw IllegalStateException("Presenter $presenter is unsuitable.")
    }

    private fun setStatusSpinnerForMediaType(v: View?) {
        val statusAdapter = ArrayAdapter.createFromResource(
                context,
                if (mediaTypeIsAnime()) R.array.anime_statuses
                else if (mediaTypeIsManga()) R.array.manga_statuses
                else throw IllegalArgumentException("Cannot set status spinner for media type: " + mediaType?.type),
                R.layout.simple_spinner_item
        )
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        if(v != null) v.statusSpinner.adapter = statusAdapter
        else statusSpinner.adapter = statusAdapter
    }

    override fun setTitle(title: String) {
        this.title.text = title
    }

    override fun setPrivate(isPrivate: Boolean) {
        privacySwitch.isChecked = isPrivate
        setPrivateBackground(isPrivate)
    }

    @SuppressLint("NewApi")
    private fun  setPrivateBackground(isPrivate: Boolean) {
        // previously invisible view
        val view = isPrivateBackground
        var animator: Animator? = null
        val isLollipopOrGreater = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP

        /**
         * if [privacySwitchTouchX] or [privacySwitchTouchY] are null we cannot animate
         */
        if (isLollipopOrGreater && privacySwitchTouchX != null && privacySwitchTouchY != null) {
            val startX: Int = (privacySwitch.left + privacySwitchTouchX!!).toInt()
            val startY: Int = (privacySwitch.top + privacySwitchTouchY!!).toInt()
            val startRadius = 0
            val endRadius = Math.hypot(view.width.toDouble(), view.height.toDouble())
            // set animator to expand or contract depending on the resultant state
            animator = ViewAnimationUtils.createCircularReveal(view, startX, startY,
                    if (isPrivate) startRadius.toFloat() else endRadius.toFloat(),
                    if (isPrivate) endRadius.toFloat() else startRadius.toFloat())
        }

        // make the view visible and start the animation
        view.visibility = if (isPrivate) View.VISIBLE else View.GONE
        if (isLollipopOrGreater) animator?.start()
    }

    private fun showRemovalConfirmationDialog() {
        val dialog = AlertDialog.Builder(context, R.style.LibraryUpdateDialog)
                .setTitle(R.string.delete_confirmation_title)
                .setMessage(R.string.delete_confirmation_message)
                .setPositiveButton(
                        R.string.delete_confirmation_positive,
                        { _, _ ->
                            presenter!!.removeLibraryEntry()
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
                .setTextColor(resources.getColor(R.color.text_gray))
    }

    override fun setStatus(status: Status) {
        this.statusSpinner.setSelection(statusResolver!!.getItemPosition(status))
    }

    override fun setReconsumedCount(reconsumedCount: Int) {
        rewatchedView.progress = reconsumedCount
    }

    override fun setRating(rating: Float) {
        ratingBar.rating = rating
    }

    override fun setNotes(notes: String) {
        notesInputEdit.setText(notes)
    }

    override fun setEpisodeProgress(progress: Int) {
        if (mediaTypeIsAnime()) {
            if (progressContainer.findViewById(R.id.episodesProgressView) != null) episodesProgressView.progress = progress
            else throw IllegalArgumentException("No episodesProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set episode progress for media type: " + mediaType!!.type)
    }

    override fun setMaxEpisodes(max: Int) {
        if (mediaTypeIsAnime()) {
            if (progressContainer.findViewById(R.id.episodesProgressView) != null) episodesProgressView.max = max
            else throw IllegalArgumentException("No episodesProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set max episodes for media type: " + mediaType!!.type)
    }

    override fun setChapterProgress(progress: Int) {
        if (mediaTypeIsManga()) {
            if (progressContainer.findViewById(R.id.chaptersProgressView) != null) chaptersProgressView.progress = progress
            else throw IllegalArgumentException("No chaptersProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set chapter progress for media type: " + mediaType!!.type)
    }

    override fun setMaxChapters(max: Int) {
        if (mediaTypeIsManga()) {
            if (progressContainer.findViewById(R.id.chaptersProgressView) != null) chaptersProgressView.max = max
            else throw IllegalArgumentException("No chaptersProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set max chapters for media type: " + mediaType!!.type)
    }

    override fun setVolumeProgress(progress: Int) {
        if (mediaTypeIsManga()) {
            if (progressContainer.findViewById(R.id.volumesProgressView) != null) volumesProgressView.progress = progress
            else throw IllegalArgumentException("No volumesProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set volume progress for media type: " + mediaType!!.type)
    }

    override fun setMaxVolumes(max: Int) {
        if (mediaTypeIsManga()) {
            if (progressContainer.findViewById(R.id.volumesProgressView) != null) volumesProgressView.max = max
            else throw IllegalArgumentException("No volumesProgressView was inflated.")
        } else throw IllegalArgumentException("Cannot set max volumes for media type: " + mediaType!!.type)
    }

}