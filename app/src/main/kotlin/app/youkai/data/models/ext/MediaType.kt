package app.youkai.data.models.ext

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
    }
}
