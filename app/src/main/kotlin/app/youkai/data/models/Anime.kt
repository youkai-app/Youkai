package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.Type

@Type("anime") @JsonIgnoreProperties(ignoreUnknown = true) //TODO: REMOVE @JsonIgnoreProperties
class Anime : BaseJsonModel() {

    var slug : String? = null

    var synopsis: String? = null

    var coverImageTopOffset: Int? = null

    var titles: Titles? = null

    var canonicalTitle: String? = null

    var abbreviatedTitles: Array<String>? = null

    var averageRating: Double? = null

    var ratingFrequencies: Map<String, String>? = null

    var favoritesCount: Int? = null

    var startDate: String? = null

    var endDate: String? = null

    var popularityRank: Int? = null

    var ratingRank: Int? = null

    var ageRating: String? = null

    var ageRatingGuide: String? = null

    var posterImage: PosterImage? = null

    var coverImage: CoverImage? = null

    var episodeCount: Int? = null

    val subtype: String? = null

    var episodeLength: Int? = null

    var showType: String? = null

    var youtubeVideoId: String? = null

    var nsfw: Boolean? = null

    //TODO: relationships stuff

}