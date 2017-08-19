package app.youkai.ui.feature.media

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.widget.Toast
import app.youkai.R
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.LibraryEntry
import app.youkai.data.models.Titles
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.feature.media.summary.SummaryFragment
import app.youkai.util.ext.toVisibility
import kotlinx.android.synthetic.main.activity_media.*
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState

class MediaActivity : MvpViewStateActivity<MediaView, BaseMediaPresenter>(), MediaView {

    override fun createPresenter(): BaseMediaPresenter = BaseMediaPresenter()

    override fun createViewState(): ViewState<MediaView> = MediaState()

    override fun onNewViewStateInstance() {
        /* do nothing */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        /* Set up title show/hide */
        appbar.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + verticalOffset == 0) {
                    toolbar.setTitleTextColor(resources.getColor(android.R.color.white))
                    isShow = true
                } else if (isShow) {
                    toolbar.setTitleTextColor(resources.getColor(android.R.color.transparent))
                    isShow = false
                }
            }
        })

        var mediaId: String = ""
        var type = MediaType.NO_IDEA
        var media: BaseMedia? = null
        var libraryEntry: LibraryEntry? = null

        val extras = intent.extras
        if (extras != null) {
            mediaId = extras.getString(ARG_ID)
            type = MediaType.fromString(extras.getString(ARG_TYPE))
            // TODO: Real get
            media = null
            libraryEntry = null
        }

        when (type) {
            MediaType.ANIME -> setPresenter(AnimePresenter())
            MediaType.MANGA -> setPresenter(MangaPresenter())
        }
        presenter.attachView(this)

        presenter.set(mediaId, media, libraryEntry)

        // TODO: We should probably handle the odd case (aka "wtf u doin, dev" case) when no id or
        // type are passed with the extras. Some fancy-ish empty state with "uhhh, something went wrong"
        // should be fine. Probably. Or maybe even a Snackbar. We shall see.

        titleView.setOnClickListener {
            presenter.onTitleClicked()
        }

        alternativeTitles.setOnClickListener {
            presenter.onAlternativeTitlesClicked()
        }

        trailer.setOnClickListener {
            presenter.onTrailerClicked()
        }

        fab.setOnClickListener {
            presenter
        }
    }

    override fun setSummary(media: BaseMedia) {
        (summaryFragment as SummaryFragment).setMedia(media, onTablet())
    }

    override fun setPoster(url: String?) {
        poster.setImageURI(url)
    }

    override fun setCover(url: String?) {
        cover.setImageURI(url)
    }

    override fun setTitle(title: String) {
        toolbar.title = title
        this.titleView.text = title
    }

    override fun setAlternativeTitlesButtonVisible(visible: Boolean) {
        alternativeTitles.visibility = visible.toVisibility()
    }

    override fun setTrailerButtonVisible(visible: Boolean) {
        trailer.visibility = visible.toVisibility()
    }

    override fun setFavorited(favorited: Boolean) {
        // TODO: Implement later
    }

    override fun setType(type: String) {
        this.type.text = type
    }

    override fun setReleaseSummary(summary: String) {
        releaseSummary.text = summary
    }

    override fun setAgeRating(rating: String) {
        ageRating.text = rating
    }

    override fun setFabIcon(res: Int) {
        fab.setImageResource(res)
    }

    override fun enableFab(enable: Boolean) {
        fab?.imageAlpha = if (!enable) {
            153 // 60%
        } else {
            255 // 100%
        }
        fab?.isEnabled = enable
    }

    override fun showFullscreenCover() {
        // TODO: Implement later
    }

    override fun showFullscreenPoster() {
        // TODO: Implement later
    }

    override fun showAlternativeTitles(titles: Titles?, abbreviatedTitles: List<String>?) {
        AlertDialog.Builder(this@MediaActivity)
                .setTitle(R.string.title_alternative_titles)
                .setMessage(this@MediaActivity.resources.getString(
                        R.string.content_alternative_titles_message,
                        titles?.en ?: "?",
                        titles?.enJp ?: "?",
                        titles?.jaJp ?: "?",
                        abbreviatedTitles?.joinToString(", ")
                ))
                .setPositiveButton(R.string.ok, null)
                .show()
    }

    override fun showTrailer(videoId: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://youtube.com/watch?v=$videoId")))
    }

    override fun showLibraryEdit() {
        /*val bottomSheetDialogFragment = EditBottomSheetFragment()
        bottomSheetDialogFragment.show(supportFragmentManager, bottomSheetDialogFragment.tag)*/
    }

    override fun showToast(text: String) {
        Toast.makeText(this@MediaActivity, text, Toast.LENGTH_LONG).show()
    }

    override fun tellChildrenLoading() {
        (summaryFragment as SummaryFragment).lceListener.onLoading()
    }

    override fun tellChildrenContent() {
        (summaryFragment as SummaryFragment).lceListener.onContent()
    }

    override fun tellChildrenError(e: Throwable) {
        (summaryFragment as SummaryFragment).lceListener.onError(e)
    }

    override fun onTablet(): Boolean {
        // TODO: Real check
        return false
    }

    companion object {
        val ARG_TYPE = "type"

        val ARG_ID = "id"
        val ARG_MEDIA = "media"
        val ARG_LIBRARY_ENTRY = "library_entry"

        fun new(context: Context, id: String, type: MediaType, media: BaseMedia?, libraryEntry: LibraryEntry?): Intent {
            val intent = Intent(context, MediaActivity::class.java)
            intent.putExtra(ARG_ID, id)
            intent.putExtra(ARG_TYPE, type.toString())
            // TODO: Parcelable stuff
//            if (media != null) intent.putExtra(ARG_MEDIA, media)
//            if (libraryEntry != null) intent.putExtra(ARG_LIBRARY_ENTRY, libraryEntry)
            return intent
        }
    }
}