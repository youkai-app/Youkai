package app.youkai.data.models

import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("reviews")
class Review : BaseJsonModel() {

    var content: String? = null

    var contentFormatted: String? = null

    var likesCount: Int? = null

    var progress: Int? = null

    var rating: Double? = null

    var source: String? = null

    var spoiler: Boolean? = null

    var createdAt: String? = null

    var updatedAt: String? = null

    /*
     * TODO: LibraryEntry model
    @Relationship("libraryEntry")
    var libraryEntry: LibraryEntry? = null

    @RelationshipLinks("libraryEntry")
    var libraryEntryLinks: Links? = null
    */

    @Relationship("media")
    var media: Media? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("user")
    var userLinks: Links? = null

}