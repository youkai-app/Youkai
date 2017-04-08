package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("reviews") @JsonIgnoreProperties(ignoreUnknown = true)
class Review : BaseJsonModel(JsonType("titles")) {

    companion object FieldNames {
        val CONTENT = "content"
        val CONTENT_FORMATTED = "contentFormatted"
        val LIKES_COUNT = "likesCount"
        val PROGRESS = "progress"
        val RATING = "rating"
        val SOURCE = "source"
        val SPOILER = "spoiler"
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val LIBRARY_ENTRY = "libraryEntry"
        val MEDIA = "media"
        val USER = "user"
    }

    var content: String? = null

    var contentFormatted: String? = null

    var likesCount: Int? = null

    var progress: Int? = null

    var rating: Double? = null

    var source: String? = null

    var spoiler: Boolean? = null

    var createdAt: String? = null

    var updatedAt: String? = null

    @Relationship("libraryEntry")
    var libraryEntry: LibraryEntry? = null

    @RelationshipLinks("libraryEntry")
    var libraryEntryLinks: Links? = null

    //TODO: Fix for polymorphism.
    @Relationship("media")
    var media: Media? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("user")
    var userLinks: Links? = null

}