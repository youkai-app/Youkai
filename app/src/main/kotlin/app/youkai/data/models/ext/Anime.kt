package app.youkai.data.models.ext

import app.youkai.data.models.Anime
import app.youkai.data.models.Media

/**
 * Converts an Anime to a Media.
 */
fun Anime.toMedia(): Media {
    val media = Media()
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