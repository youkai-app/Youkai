package app.youkai.ui.feature.media.summary

import app.youkai.App
import app.youkai.R
import app.youkai.data.models.Anime
import app.youkai.data.models.StreamingLink
import app.youkai.data.models.ext.releaseStatus
import app.youkai.data.models.ext.season
import app.youkai.data.models.ext.toCharactersList
import app.youkai.util.ext.append
import app.youkai.util.ext.stripToDate
import app.youkai.util.ext.toMonthDayYearString
import app.youkai.util.ext.toTimestamp

/**
 * Media summary presenter that takes care of anime-specific functions.
 */
class AnimeSummaryPresenter : BaseSummaryPresenter() {

    override fun setLength() {
        val res = App.context.resources

        val episodeCount = (media as Anime).episodeCount ?: 0
        val episodeLength = (media as Anime).episodeLength ?: 0
        val totalHours = (episodeCount * episodeLength) / 60

        val episodesText = res.getQuantityString(R.plurals.x_episodes, episodeCount, episodeCount)
        val lengthText = res.getQuantityString(
                R.plurals.x_minutes_each_y_hours_total,
                episodeCount,
                res.getQuantityString(R.plurals.x_minutes, episodeLength, episodeLength),
                res.getQuantityString(R.plurals.x_hours, totalHours, totalHours)
        )
        view?.setLength(episodesText, lengthText, true)
        view?.setLengthIcon(R.drawable.ic_length_anime_36dp)
    }

    override fun setStreamers() {
        if (!onTablet) {
            val services = (media as Anime?)?.streamingLinks
            if (services != null) {
                view?.setStreamers(services)
            } else {
                view?.setNoStreamingServices()
            }
        } else {
            view?.setNoStreamingServices()
        }
    }

    /**
     * Does all the necessary calculations and decisions and figures out the best way to display
     * airing information of an anime. Plural strings are used to simplify otherwise
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
        val media = (media as Anime)

        val status = media.releaseStatus().value
        val start = media.startDate
        val end = media.endDate
        var airText = ""
        val startText = start?.toTimestamp()?.toMonthDayYearString() ?: ""
        val endText = end?.toTimestamp()?.toMonthDayYearString() ?: ""

        if (start != null && end != null) {
            if (start == end) {
                // Airing today???
                if (start.toTimestamp() == System.currentTimeMillis().stripToDate()) {
                    airText = res.getString(R.string.air_status_today)
                } else {
                    airText = res.getQuantityString(R.plurals.air_status_x_to_x, status, startText)
                }
            } else {
                airText = res.getQuantityString(
                        R.plurals.air_status_from_x_to_y,
                        status,
                        startText,
                        endText
                )
            }
        } else if (start != null && end == null) {
            airText = res.getQuantityString(R.plurals.air_status_on_x, status, startText)
        } else if (start == null && end != null) {
            airText = res.getQuantityString(R.plurals.air_status_to_y, status, endText)
        } else if (start == null && end == null) {
            airText = res.getString(R.string.air_status_no_date_info)
        }

        view?.setReleaseInfo(airText,
                if (start != null) {
                    res.getString(
                            media.season().valueWithYear,
                            start.split("-")[0]
                    )
                } else {
                    res.getString(R.string.air_status_no_season_info)
                },
                true
        )
    }

    override fun setProducers() {
        val media = (media as Anime)

        var producers = ""

        media.productions?.forEach {
            val name = it.producer?.name
            if (name != null) producers = producers.append(name, ", ")
        }

        view?.setProducers(
                if (producers.isNotEmpty()) {
                    producers
                } else {
                    App.context.resources.getString(R.string.no_producer_info)
                }
        )
        view?.setProducersIcon(R.drawable.ic_producer_anime_36dp)
    }

    override fun setCharacters() {
        val media = (media as Anime)

        view?.setCharacters(media.animeCharacters?.toCharactersList() ?: arrayListOf())
        view?.setMoreCharactersCount((media.animeCharacters?.size ?: 5) - 5)
    }

    override fun setRelated() {
        val related = (media as Anime?)?.medias
        if (related != null) {
            view?.setRelated(related)
        } else {
            view?.setRelatedVisible(false)
        }
    }

    override fun onLengthClicked() {
        view?.onLengthClicked()
    }

    override fun onReleaseInfoClicked() {
        view?.onReleaseInfoClicked()
    }
}