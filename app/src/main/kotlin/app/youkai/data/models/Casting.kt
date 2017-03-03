package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.*
import com.github.jasminb.jsonapi.annotations.Meta

@Type("castings")
class Casting : BaseJsonModel() {

    var role: String? = null

    @JsonProperty("voiceActor")
    var isVoiceActor: Boolean? = null

    var featured: Boolean? = null

    var language: String? = null

    @Meta
    var meta: CountMeta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

    //TODO: relationships, character, person

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

}