package app.youkai.ui.feature.media.characters

import com.hannesdorfmann.mosby.mvp.MvpView

interface CharactersActivityView : MvpView {
    fun setUpLanguageSelector(languages: List<String>)
    fun setLanguage(index: Int)

    fun passErrorToFragment(e: Throwable)
    fun passLanguageToFragment(language: String)
}