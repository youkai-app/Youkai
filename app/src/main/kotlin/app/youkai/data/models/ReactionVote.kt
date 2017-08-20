package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("mediaReactionVotes") @JsonIgnoreProperties(ignoreUnknown = true)
class ReactionVote : BaseJsonModel(JsonType("mediaReactionVotes")) {

    companion object FieldNames {
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val REACTION = "mediaReaction"
        val USER = "user"
    }

    var createdAt: String? = null

    var updatedAt: String? = null

    @Relationship("mediaReaction")
    var reaction: Reaction? = null

    @RelationshipLinks("mediaReaction")
    var reactionLinks: Links? = null

    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("userLinks")
    var userLinks: Links? = null

}