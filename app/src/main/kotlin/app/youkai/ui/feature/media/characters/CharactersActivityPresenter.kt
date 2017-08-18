package app.youkai.ui.feature.media.characters

import android.content.Intent
import app.youkai.data.service.Api
import app.youkai.util.ext.whenNotNull
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CharactersActivityPresenter : MvpBasePresenter<CharactersActivityView>() {

    fun load(intent: Intent) {
        Api.languagesForAnime(intent.extras.getString(CharactersActivity.ARG_MEDIA_ID)).get()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { languages ->
                            whenNotNull(languages) {
                                view?.setUpLanguageSelector(languages)
                            }
                        },
                        { e ->
                            view?.passErrorToFragment(e)
                        },
                        {
                            // onComplete
                        }
                )
    }

    fun onLanguageSelected(language: String) {
        view?.passLanguageToFragment(language)
    }

    fun onLanguageSpinnerSetUp(languages: List<String>) {
        // Prefer Japanese if it's there, else just go with the first.
        val defaultLang = if (languages.contains("Japanese")) languages.indexOf("Japanese") else 0
        view?.setLanguage(defaultLang)
    }
}