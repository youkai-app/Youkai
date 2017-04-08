package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.Type

@Type("titles") @JsonIgnoreProperties(ignoreUnknown = true)
class Titles : BaseJsonModel("titles") {

    companion object FieldNames {
        val EN = "en"
        val EN_JP = "en_jp"
        val JA_JP = "ja_jp"
    }

    @JsonProperty("en")
    var en: String? = null

    @JsonProperty("en_jp")
    var enJp: String? = null

    @JsonProperty("ja_jp")
    var jaJp: String? = null

}