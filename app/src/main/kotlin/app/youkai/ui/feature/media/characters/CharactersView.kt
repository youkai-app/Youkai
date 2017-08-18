package app.youkai.ui.feature.media.characters

import app.youkai.data.models.Casting
import app.youkai.ui.common.mvp.MvpLceView

interface CharactersView : MvpLceView {
    fun setMediaTitle(title: String)
    fun setCharacters(characters: List<Casting>, add: Boolean)

    fun showBottomProgressBar(show: Boolean = true)
}