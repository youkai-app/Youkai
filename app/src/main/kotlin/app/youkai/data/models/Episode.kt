package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("episodes") @JsonIgnoreProperties(ignoreUnknown = true)
class Episode : BaseJsonModel(JsonType("episodes")) {

    companion object FieldNames {
        val TITLES = "titles"
        val CANONICAL_TITLE = "canonicalTitle"
        val SEASON_NUMBER = "seasonNumber"
        val NUMBER = "number"
        val SYNOPSIS = "synopsis"
        val AIR_DATE = "airdate"
        val LENGTH = "length"
        val THUMBNAIL = "thumbnail"
        val MEDIA = "media"
    }

    var titles: Titles? = null

    var canonicalTitle: String? = null

    var seasonNumber: Int? = null

    var number: Int? = null

    var synopsis: String? = null

    var airdate: String? = null

    var length: Int? = null

    var thumbnail: Image? = null

    @Relationship("media")
    var media: BaseMedia? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

}