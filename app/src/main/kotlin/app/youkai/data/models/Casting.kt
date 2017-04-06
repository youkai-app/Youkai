package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("castings") @JsonIgnoreProperties(ignoreUnknown = true)
class Casting : BaseJsonModel() {

    companion object FieldNames {
        val TYPE = "castings"
        val ROLE = "role"
        val VOICE_ACTOR = "voiceActor"
        val FEATURED = "featured"
        val LANGUAGE = "language"
        val PERSON = "person"
        val MEDIA = "media"
        val CHARACTER = "character"
    }

    var role: String? = null

    @JsonProperty("voiceActor")
    var isVoiceActor: Boolean? = null

    var featured: Boolean? = null

    var language: String? = null

    @Relationship("person")
    var person: Person? = null

    @RelationshipLinks("person")
    var personLinks: Links? = null

    //TODO: Fix for polymorphism.
    @Relationship("media")
    var media: Media? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

    @Relationship("character")
    var character: Character? = null

    @RelationshipLinks("character")
    var characterLinks: Links? = null

}