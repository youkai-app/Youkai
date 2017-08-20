package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("mediaReactions") @JsonIgnoreProperties(ignoreUnknown = true)
class Reaction : BaseJsonModel(JsonType("mediaReactions")) {

    companion object FieldNames {
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val REACTION = "reaction"
        val UPVOTES_COUNT = "upVotesCount"
        val ANIME = "anime"
        val MANGA = "manga"
        val DRAMA = "drama"
        val USER = "user"
        val LIBRARY_ENTRY = "libraryEntry"
        val VOTES = "votes"
    }

    var createdAt: String? = null

    var updatedAt: String? = null

    var reaction: String? = null

    var upvotesCount: Int? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("manga")
    var manga: Manga? = null
/*
    @Relationship("drama")
    var drama: Drama? = null

    @Relationship("drama")
    var dramaLinks: Links? = null
*/
    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("user")
    var userLinks: Links? = null

    @Relationship("libraryEntry")
    var libraryEntry: LibraryEntry? = null

    @RelationshipLinks("libraryEntry")
    var libraryEntryLinks: Links? = null

    @Relationship("votes")
    var votes: List<ReactionVote>? = null

    @RelationshipLinks("votes")
    var votesLinks: Links? = null

}