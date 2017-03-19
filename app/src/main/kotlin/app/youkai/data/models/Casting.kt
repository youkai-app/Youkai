package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("castings")
class Casting : BaseJsonModel() {

    var role: String? = null

    @JsonProperty("voiceActor")
    var isVoiceActor: Boolean? = null

    var featured: Boolean? = null

    var language: String? = null

    @Relationship("person")
    var person: Person? = null

    @RelationshipLinks("person")
    var personLinks: Person? = null

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: Links? = null

    @Relationship("character")
    var character: Character? = null

    @RelationshipLinks("chacacter")
    var characterLinks: Links? = null

}