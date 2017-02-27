package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.Links
import com.github.jasminb.jsonapi.annotations.Meta
import com.github.jasminb.jsonapi.annotations.Type

@Type("castings")
class Castings : BaseJsonModel() {

    var role: String? = null

    @JsonProperty("voiceActor")
    var isVoiceActor: Boolean? = null

    var featured: Boolean? = null

    var language: String? = null

    @Meta
    var meta: CastingsMeta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

}