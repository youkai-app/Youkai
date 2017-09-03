package app.youkai.ui.feature.library_update

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.youkai.R
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.feature.library_update.view.AnimeLibraryUpdateView
import app.youkai.ui.feature.library_update.view.LibraryUpdateView
import app.youkai.ui.feature.library_update.view.LibraryUpdateViewManager
import app.youkai.ui.feature.library_update.view.MangaLibraryUpdateView
import app.youkai.ui.feature.login.LoginActivity

/**
 * Was intended to be
 * class LibraryUpdateSheet : MvpBottomSheetFragment<LibraryUpdateView, BaseLibraryUpdatePresenter>(), LibraryUpdateView
 * but could not get MvpBottomSheetFragment to work.
 */
class LibraryUpdateSheet : BottomSheetDialogFragment(), LibraryUpdateViewManager {

    companion object {
        val ARGUMENT_LIBRARY_ENTRY_ID = "libraryEntryId"
        val ARGUMENT_ANIME_ID = "animeId"
        val ARGUMENT_MANGA_ID = "mangaId"
    }

    private var presenter: BaseLibraryUpdatePresenter? = null
    private var viewT: LibraryUpdateView? = null

    override fun getViewT() = viewT

    /**
     * Lifecycle methods
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPresenterForArguments()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.library_update, container, false)
        setMediaTypeForArguments(v)
        return v
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // manually calling presenter method
        presenter?.detachView(retainInstance = false)
        viewT = null //TODO: check if need to anything for other life cycle methods
    }

    override fun onPause() {
        /**
         * TODO: change behaviour
         * Save when:
         *  - dismissed
         *  - screen off
         *  - switched app
         *  -
         */
        presenter?.postUpdate()

        super.onPause()
    }

    /**
     * Media type configuration
     */
    private fun setPresenterForArguments() {
        if (arguments.containsKey(ARGUMENT_LIBRARY_ENTRY_ID)) {
            presenter = BaseLibraryUpdatePresenter()
            presenter!!.getEntryById(arguments.getString(ARGUMENT_LIBRARY_ENTRY_ID))
        } else if (arguments.containsKey(ARGUMENT_ANIME_ID)) {
            presenter = AnimeLibraryUpdatePresenter()
            (presenter as AnimeLibraryUpdatePresenter).getEntryByAnime(arguments.getString(ARGUMENT_ANIME_ID))
        } else if (arguments.containsKey(ARGUMENT_MANGA_ID)) {
            presenter = MangaLibraryUpdatePresenter()
            (presenter as MangaLibraryUpdatePresenter).getEntryByManga(arguments.getString(ARGUMENT_MANGA_ID))
        } else throw IllegalArgumentException("Cannot create a LibraryUpdateSheet without any relevant arguments.")
        presenter!!.attachView(this)
    }

    /**
     * This exists so that the exposed interface remains simple and without View dependencies.
     */
    override fun setMediaType(mediaType: MediaType) = setMediaType(mediaType, view)

    /**
     * Use directly if calling this before [LibraryUpdateSheet.onCreateView] has finished.
     */
    private fun setMediaType(mediaType: MediaType, rootView: View? = null) {
        if (mediaType == MediaType.NO_IDEA) return

        if (!mediaTypeIsAnime(mediaType) && !mediaTypeIsManga(mediaType))
            throw IllegalArgumentException("Media type " + mediaType.value + " is unrecognised.")

        replacePresenterForMediaType(mediaType)
        setViewT(mediaType, rootView)
    }

    private fun setMediaTypeForArguments(rootView: View?) {
        if (arguments.containsKey(ARGUMENT_LIBRARY_ENTRY_ID)) {
            setMediaType(MediaType.NO_IDEA, rootView)
        } else if (arguments.containsKey(ARGUMENT_ANIME_ID)) {
            setMediaType(MediaType.ANIME, rootView)
        } else if (arguments.containsKey(ARGUMENT_MANGA_ID)) {
            setMediaType(MediaType.MANGA, rootView)
        } else throw IllegalArgumentException("Cannot create a LibraryUpdateSheet without any relevant arguments.")
    }

    private fun setViewT(mediaType: MediaType, rootView: View?) {
        if (mediaTypeIsAnime(mediaType)) viewT =
                AnimeLibraryUpdateView(
                        presenter ?: throw IllegalArgumentException("No presenter."),
                        rootView ?: view ?: throw IllegalStateException("No view to configure."),
                        context)
        else if (mediaTypeIsManga(mediaType)) {
            MangaLibraryUpdateView(
                    presenter ?: throw IllegalArgumentException("No presenter."),
                    rootView ?: view ?: throw IllegalStateException("No view to configure."),
                    context)
        }
        else throw IllegalArgumentException("Media type " + mediaType.value + " is unrecognised.")
    }

    private fun replacePresenterForMediaType(mediaType: MediaType) {
        val newPresenter: BaseLibraryUpdatePresenter =
                if (mediaTypeIsAnime(mediaType)) AnimeLibraryUpdatePresenter()
                else if (mediaTypeIsManga(mediaType)) MangaLibraryUpdatePresenter()
                else throw IllegalArgumentException("No library entry presenter for type: " + mediaType.value)
        if (presenter != null) newPresenter.libraryEntry = presenter!!.libraryEntry
        presenter = newPresenter
        presenter!!.attachView(this)
    }

    //TODO: pull out
    private fun mediaTypeIsAnime(mediaType: MediaType) = mediaType == MediaType.ANIME

    private fun mediaTypeIsManga(mediaType: MediaType) = mediaType == MediaType.MANGA

    /**
     * Login boilerplate
     * //TODO: pull out
     */
    override fun sendToLogin()
            = startActivityForResult(Intent(context, LoginActivity::class.java), LoginActivity.START_FOR_ACCESS_TOKEN)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivity.START_FOR_ACCESS_TOKEN && resultCode == LoginActivity.RESULT_OK) {
            presenter?.postUpdate()
        }
    }

}