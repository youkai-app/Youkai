package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("chapters") @JsonIgnoreProperties(ignoreUnknown = true)
class Chapter : BaseJsonModel(JsonType("chapters")) {

    companion object FieldNames {
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val TITLES = "titles"
        val CANONICAL_TITLE = "canonicalTitle"
        val VOLUME_NUMBER = "volumeNumber"
        val NUMBER = "number"
        val SYNOPSIS = "synopsis"
        val PUBLISHED = "published"
        val LENGTH = "length"
        val THUMBNAIL = "thumbnail"
        val MANGA = "manga"
    }

    var createdAt: String? = null

    var updatedAt: String? = null

    var titles: Titles? = null

    var canonicalTitle: String? = null

    var volumeNumber: Int? = null

    var number: Int? = null

    var synopsis: String? = null

    var published: String? = null

    var length: Int? = null

    /*
     * Thumbnail is an object with a single field ("original") in the api.
     * This method prevents the need for creating a new object for this one property.
     * If the thumbnail object one day includes new properties (pretty much guaranteed to be strings)
     * this method should still work as expected.
     */
    var thumbnail: String? = null

    @JsonProperty("thumbnail")
    fun setThumbnail(m: Map<String, String>?) {
        if (m != null) thumbnail = m["original"]
    }

    @Relationship("manga")
    var manga: Manga? = null

    @RelationshipLinks("manga")
    var mangaLinks: Links? = null

}