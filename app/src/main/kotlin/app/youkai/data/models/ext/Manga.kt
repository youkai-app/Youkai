package app.youkai.data.models.ext

import app.youkai.App
import app.youkai.R
import app.youkai.data.models.Manga

/**
 * Manga related extension stuff
 */

/**
 * Returns the appropriate type string of this Manga.
 */
fun Manga.typeString(): String {
    return when (mangaType) {
        "manga" -> App.context.getString(R.string.media_type_manga_manga)
        "novel" -> App.context.getString(R.string.media_type_manga_novel)
        "manhua" -> App.context.getString(R.string.media_type_manga_manhua)
        "oneshot" -> App.context.getString(R.string.media_type_manga_oneshot)
        "doujin" -> App.context.getString(R.string.media_type_manga_doujin)
        else -> "?"
    } ?: mangaType ?: "?"
}