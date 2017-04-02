package app.youkai.data.models.ext

import app.youkai.data.models.Anime
import app.youkai.util.string
import app.youkai.data.models.BaseMedia
import app.youkai.R
import java.util.*

/**
 * Anime related extension stuff
 */

/**
 * Converts an Anime to a Media.
 */
fun Anime.toMedia(): BaseMedia {
    val media = BaseMedia()
    media.id = this.id
    media.links = this.links
    media.slug = this.slug
    media.synopsis = this.synopsis
    media.coverImageTopOffset = this.coverImageTopOffset
    media.titles = this.titles
    media.canonicalTitle = this.canonicalTitle
    media.abbreviatedTitles = this.abbreviatedTitles
    media.averageRating = this.averageRating
    media.ratingFrequencies = this.ratingFrequencies
    media.favoritesCount = this.favoritesCount
    media.startDate = this.startDate
    media.endDate = this.endDate
    media.popularityRank = this.popularityRank
    media.ratingRank = this.ratingRank
    media.ageRating = this.ageRating
    media.ageRatingGuide = this.ageRatingGuide
    media.posterImage = this.posterImage
    media.posterImage = this.posterImage
    media.coverImage = this.coverImage
    media.subtype = this.subtype
    media.genres = this.genres
    media.genreLinks = this.genreLinks
    media.castings = this.castings
    media.castingLinks = this.castingLinks
    media.installments = this.installments
    media.installmentLinks = this.installmentLinks
    media.mappings = this.mappings
    media.medias = this.medias
    media.mediaLinks = this.mediaLinks
    media.reviews = this.reviews
    media.reviewLinks = this.reviewLinks
    return media
}

/**
 * Figures out and returns season from an anime object based on start of airing date.
 */
fun Anime.season(): AnimeSeason {
    val parts = startDate?.split("-") ?: arrayListOf("2000", "2", "10")
    return when (parts[1].toInt() - 1) { // - 1 to normalize for the Calendar class
        in Calendar.JANUARY..Calendar.MARCH -> AnimeSeason.WINTER
        in Calendar.APRIL..Calendar.JUNE -> AnimeSeason.SPRING
        in Calendar.JULY..Calendar.SEPTEMBER -> AnimeSeason.SUMMER
        in Calendar.OCTOBER..Calendar.DECEMBER -> AnimeSeason.FALL
        else -> AnimeSeason.WINTER
    }
}

enum class AnimeSeason(val value: Int, val valueWithYear: Int) {
    WINTER(R.string.anime_season_winter, R.string.anime_season_winter_y),
    SPRING(R.string.anime_season_spring, R.string.anime_season_spring_y),
    SUMMER(R.string.anime_season_summer, R.string.anime_season_summer_y),
    FALL(R.string.anime_season_fall, R.string.anime_season_fall_y)
}

/**
 * Returns the appropriate type string of this Anime.
 */
fun Anime.typeString(): String {
    return when (showType) {
        "TV" -> string(R.string.media_type_anime_tv)
        "Special" -> string(R.string.media_type_anime_special)
        "OVA" -> string(R.string.media_type_anime_ova)
        "ONA" -> string(R.string.media_type_anime_ona)
        "Movie" -> string(R.string.media_type_anime_movie)
        "Music" -> string(R.string.media_type_anime_music)
        else -> "?"
    } ?: showType ?: "?"
}