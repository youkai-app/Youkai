package app.youkai.ui.feature.media.summary

import app.youkai.App
import app.youkai.R
import app.youkai.data.models.BaseMedia
import app.youkai.data.models.Manga
import app.youkai.data.models.ext.isMangaka
import app.youkai.data.models.ext.releaseStatus
import app.youkai.util.ext.append
import app.youkai.util.ext.stripToDate
import app.youkai.util.ext.toMonthDayYearString
import app.youkai.util.ext.toTimestamp

/**
 * Media summary presenter that takes care of manga-specific functions.
 */
class MangaSummaryPresenter : BaseSummaryPresenter() {

    override fun load(media: BaseMedia?, onTablet: Boolean) {
        super.load(media, onTablet)
        view?.setStreamersShown(false)
        view?.setCharactersShown(false)
    }

    override fun setLength() {
        val res = App.context.resources

        val chaptersCount = (media as Manga?)?.chapterCount
        val volumesCount = (media as Manga?)?.volumeCount

        val chaptersText = res.getQuantityString(
                R.plurals.x_chapters,
                chaptersCount ?: 0,
                chaptersCount ?: "?"
        )
        val volumesText = res.getQuantityString(
                R.plurals.x_volumes,
                volumesCount ?: 0,
                volumesCount ?: "?"
        )
        view?.setLength(chaptersText, volumesText, false)
        view?.setLengthIcon(R.drawable.ic_length_manga_36dp)
    }

    override fun setStreamers() {
        /* Do nothing. This is only for Anime. */
    }

    /**
     * Does all the necessary calculations and decisions and figures out the best way to display
     * publishing information of a manga. Plural strings are used to simplify otherwise
     * complex code with many nested if statements.
     *
     * Essentially, we have three main status cases. These are past, present, and future
     * (see [app.youkai.data.models.ext.ReleaseStatus]). These statuses have integer values which are used
     * to grab the relevant items from our plural values.
     *
     * Then, we have if statements covering all possible combinations of start and end dates
     * existing and not existing. We also have a bonus check for the current day.
     *
     * Finally, since our view is as dumb as possible, we simply pass two strings for the two lines
     * in our BarInfoView and an extra boolean flag for making the second line a link.
     */
    override fun setReleaseInfo() {
        val res = App.context.resources
        val media = (media as Manga)

        val status = media.releaseStatus().value
        val start = media.startDate
        val end = media.endDate
        var airText = ""
        val startText = start?.toTimestamp()?.toMonthDayYearString() ?: ""
        val endText = end?.toTimestamp()?.toMonthDayYearString() ?: ""

        if (start != null && end != null) {
            if (start == end) {
                // Publishing today???
                if (start.toTimestamp() == System.currentTimeMillis().stripToDate()) {
                    airText = res.getString(R.string.publish_status_today)
                } else {
                    airText = res.getQuantityString(R.plurals.publish_status_x_to_x, status, startText)
                }
            } else {
                airText = res.getQuantityString(
                        R.plurals.publish_status_from_x_to_y,
                        status,
                        startText,
                        endText
                )
            }
        } else if (start != null && end == null) {
            airText = res.getQuantityString(R.plurals.publish_status_on_x, status, startText)
        } else if (start == null && end != null) {
            airText = res.getQuantityString(R.plurals.publish_status_to_y, status, endText)
        } else if (start == null && end == null) {
            airText = res.getString(R.string.publish_status_no_info)
        }

        val serialization = if (media.serialization?.isNotEmpty() ?: false) {
            media.serialization!!
        } else {
            res.getString(R.string.publish_status_serialization_unknown)
        }

        view?.setReleaseInfo(airText, serialization, false)
    }

    override fun setProducers() {
        val media = (media as Manga)

        var producers = ""

        media.castings?.forEach {
            val name = it.person?.name
            if (it.isMangaka() && name != null) {
                producers = producers.append(name, ", ")
            }
        }

        view?.setProducers(
                if (producers.isNotEmpty()) {
                    producers
                } else {
                    App.context.resources.getString(R.string.no_mangaka_info)
                }
        )
        view?.setProducersIcon(R.drawable.ic_producer_manga_36dp)
    }

    override fun setCharacters() {
        /* Do nothing. This is only for Anime. */
    }

    override fun setRelated() {
        val related = (media as Manga?)?.medias
        if (related != null) {
            view?.setRelated(related)
        } else {
            view?.setRelatedVisible(false)
        }
    }

    override fun onLengthClicked() {
        // TODO
    }

    override fun onReleaseInfoClicked() {
        // TODO
    }
}
