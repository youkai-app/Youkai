package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.*

@Type("castings")
class Casting : BaseJsonModel() {

    var role: String? = null

    @JsonProperty("voiceActor")
    var isVoiceActor: Boolean? = null

    var featured: Boolean? = null

    var language: String? = null

    //TODO: relationships: person

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("character")
    var character: Character? = null

    @RelationshipLinks("chacacter")
    var characterLinks: com.github.jasminb.jsonapi.Links? = null

}