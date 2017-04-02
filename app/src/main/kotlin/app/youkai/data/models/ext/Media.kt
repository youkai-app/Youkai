package app.youkai.data.models.ext

import app.youkai.App
import app.youkai.App.Companion.context
import app.youkai.R
import app.youkai.data.models.Anime
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Manga
import app.youkai.util.ext.toTimestamp

/**
 * Media related extensions
 */

/**
 * Figures out and returns air status from an anime object based on start and end dates of airing.
 */
@Suppress("ConvertTwoComparisonsToRangeCheck")
fun BaseMedia.releaseStatus(): ReleaseStatus {
    var status = ReleaseStatus.PAST // PAST by default

    val now = System.currentTimeMillis()
    val start = startDate?.toTimestamp() ?: 0
    val end = endDate?.toTimestamp() ?: now + 60000 // If doesn't exit, assume infinity. :kappa:

    if (start > now) {
        status = ReleaseStatus.FUTURE
    } else if (start < now && end > now) {
        status = ReleaseStatus.PRESENT
    } else if (end < now) {
        status = ReleaseStatus.PAST
    }

    return status
}

fun BaseMedia.releaseSummary(): String {
    return when (releaseStatus()) {
        ReleaseStatus.FUTURE -> when (this) {
            is Anime -> App.context.getString(R.string.media_release_summary_anime_future)
            is Manga -> App.context.getString(R.string.media_release_summary_manga_future)
            else -> ""
        }
        ReleaseStatus.PRESENT -> when (this) {
            is Anime -> App.context.getString(R.string.media_release_summary_anime_present)
            is Manga -> App.context.getString(R.string.media_release_summary_manga_present)
            else -> ""
        }
        ReleaseStatus.PAST -> when (this) {
            is Anime -> App.context.getString(R.string.media_release_summary_anime_past)
            is Manga -> App.context.getString(R.string.media_release_summary_manga_past)
            else -> ""
        }
    }
}

enum class ReleaseStatus(val value: Int) {
    FUTURE(0), PRESENT(1), PAST(2)
}
