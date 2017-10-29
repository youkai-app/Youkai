package app.youkai.ui.feature.media.characters

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import app.youkai.R
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.common.app.FragmentContainerActivity

class CharactersActivity : FragmentContainerActivity<CharactersActivityView, CharactersActivityPresenter>(), CharactersActivityView {

    override fun createPresenter(): CharactersActivityPresenter = CharactersActivityPresenter()

    private var fragment: CharactersFragment? = null

    private var spinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.load(intent)
    }

    override fun newFragment(): Fragment {
        val extras = intent.extras
        val id = extras.getString(ARG_MEDIA_ID)
        val type = extras.getString(ARG_MEDIA_TYPE)
        val title = extras.getString(ARG_MEDIA_TITLE)
        fragment = CharactersFragment.new(id, MediaType.fromString(type), title)
        return fragment!!
    }

    @SuppressLint("RtlHardcoded")
    override fun setUpLanguageSelector(languages: List<String>) {
        val adapter = ArrayAdapter<String>(this, R.layout.item_spinner_language, languages)
        adapter.setDropDownViewResource(R.layout.item_spinner_language_dropdown)
        spinner = layoutInflater.inflate(
                R.layout.view_spinner_toolbar,
                getToolbar(),
                false
        ) as Spinner
        spinner?.adapter = adapter

        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            var currentLanguage: String = ""

            override fun onNothingSelected(parent: AdapterView<*>?) {
                /* do nothing */
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = adapter.getItem(position)
                if (currentLanguage != selectedLanguage) {
                    presenter.onLanguageSelected(selectedLanguage)
                    currentLanguage = selectedLanguage
                }
            }
        }

        getToolbar().addView(spinner)

        presenter.onLanguageSpinnerSetUp(languages)
    }

    override fun setLanguage(index: Int) {
        spinner?.setSelection(index)
    }

    override fun passErrorToFragment(e: Throwable) {
        fragment?.errorListener?.invoke(e)
    }

    override fun passLanguageToFragment(language: String) {
        fragment?.languageChangedListener?.invoke(language)
    }

    companion object {
        const val ARG_MEDIA_ID = "media_id"
        const val ARG_MEDIA_TYPE = "media_type"
        const val ARG_MEDIA_TITLE = "media_title"

        fun getLaunchIntent(context: Context, mediaId: String, mediaType: MediaType,
                mediaTitle: String): Intent {
            val intent = Intent(context, CharactersActivity::class.java)
            intent.putExtra(ARG_MEDIA_ID, mediaId)
            intent.putExtra(ARG_MEDIA_TYPE, mediaType.value)
            intent.putExtra(ARG_MEDIA_TITLE, mediaTitle)
            return intent
        }
    }
}
