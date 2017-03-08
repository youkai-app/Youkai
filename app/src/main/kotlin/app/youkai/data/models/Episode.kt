package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("episodes")
class Episode : BaseJsonModel() {

    var titles: Titles? = null

    var canonicalTitle: String? = null

    var seasonNumber: Int? = null

    var number: Int? = null

    var synopsis: String? = null

    var airdate: String? = null

    var length: Int? = null

    /*
     * Thumbnail is an object with a single field ("original") in the api.
     * This method prevents the need for creating a new object for this one property.
     * If the thumbnail object one day includes new properties (pretty much guaranteed to be strings)
     * this method should still work as expected.
     */
    var thumbnail: String? = null

    @JsonProperty("thumbnail")
    fun setThumbnail(m: Map<String, String>) {
        thumbnail = m["original"];
    }

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: Links? = null

}