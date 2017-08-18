package app.youkai.ui.feature.media.characters

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ViewFlipper
import app.youkai.R
import app.youkai.data.models.Casting
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.common.mvp.MvpLceFragment
import kotlinx.android.synthetic.main.fragment_characters.*
import tr.xip.errorview.ErrorView
import kotlinx.android.synthetic.main.fragment_characters.view.*
import app.youkai.ui.common.view.GridSpacingItemDecoration
import app.youkai.ui.common.view.RecyclerViewEndlessScrollListener
import app.youkai.util.ext.toPx
import app.youkai.util.ext.toVisibility
import app.youkai.util.ext.whenNotNull

class CharactersFragment : MvpLceFragment<CharactersView, CharactersPresenter>(), CharactersView {

    val languageChangedListener: (language: String) -> Unit = {
        recycler.smoothScrollToPosition(0)
        presenter.load(language = it)
    }

    val errorListener: (e: Throwable) -> Unit = {
        presenter.onError(it)
    }

    override fun createPresenter() = CharactersPresenter()

    override fun getViewFlipper(): ViewFlipper = view!!.flipper

    override fun getErrorView(): ErrorView = view!!.errorView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mediaId = arguments.getString(ARG_MEDIA_ID)
        val mediaType = MediaType.fromString(arguments.getString(ARG_MEDIA_TYPE))
        val mediaTitle = arguments.getString(ARG_MEDIA_TITLE)

        setUpRecyclerView()

        presenter.set(mediaId, mediaType, mediaTitle)
    }

    override fun setMediaTitle(title: String) {
        (activity as AppCompatActivity?)?.supportActionBar?.subtitle = title
    }

    override fun setCharacters(characters: List<Casting>, add: Boolean) {
        if (!add) {
            val adapter = CharactersAdapter()
            adapter.dataset.addAll(characters)
            adapter.notifyDataSetChanged()
            recycler.adapter = adapter
        } else {
            whenNotNull(recycler.adapter as CharactersAdapter?) {
                it.dataset.addAll(characters)
                it.notifyDataSetChanged()
            }
        }
        bottomProgressBar.visibility = View.GONE
    }

    private fun setUpRecyclerView() {
        val columnsCount = maximumTilesCount()
        recycler.layoutManager = GridLayoutManager(context, columnsCount)
        recycler.addItemDecoration(GridSpacingItemDecoration(columnsCount, 4.toPx(context), true))
        recycler.addOnScrollListener(RecyclerViewEndlessScrollListener(recycler.layoutManager) {
            presenter.onScrollEndReached()
        })
    }

    private fun maximumTilesCount(): Int {
        return 2 // TODO: Calc
    }

    override fun showBottomProgressBar(show: Boolean) {
        bottomProgressBar.visibility = show.toVisibility()
    }



    companion object {
        val ARG_MEDIA_ID = "media_id"
        val ARG_MEDIA_TYPE = "media_type"
        val ARG_MEDIA_TITLE = "media_title"

        fun new(mediaId: String, mediaType: MediaType, mediaTitle: String): CharactersFragment {
            val fragment = CharactersFragment()
            val ars = Bundle()
            ars.putString(ARG_MEDIA_ID, mediaId)
            ars.putString(ARG_MEDIA_TYPE, mediaType.value)
            ars.putString(ARG_MEDIA_TITLE, mediaTitle)
            fragment.arguments = ars
            return fragment
        }
    }
}