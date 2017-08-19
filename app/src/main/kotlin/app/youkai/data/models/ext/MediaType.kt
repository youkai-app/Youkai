package app.youkai.data.models.ext

import app.youkai.data.models.Anime
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Manga

/**
 * Media types
 */
enum class MediaType(val value: String) {
    ANIME("anime"), MANGA("manga"), NO_IDEA("no_idea");

    override fun toString(): String {
        return value
    }

    companion object {
        fun fromString(string: String): MediaType {
            return when (string) {
                "anime" -> ANIME
                "manga" -> MANGA
                else -> NO_IDEA
            }
        }

        fun fromObject(media: BaseMedia?): MediaType {
            if (media == null) return NO_IDEA

            return when (media) {
                is Anime -> ANIME
                is Manga -> MANGA
                else -> NO_IDEA
            }
        }
    }
}
