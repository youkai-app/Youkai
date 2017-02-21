package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.Type

@Type("titles")
class Titles : BaseJsonModel() {

    @JsonProperty("en")
    var en: String? = null

    @JsonProperty("en_jp")
    var enJp: String? = null

    @JsonProperty("ja_jp")
    var jaJp: String? = null

}