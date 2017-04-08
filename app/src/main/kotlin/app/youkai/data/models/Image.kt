package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("image") @JsonIgnoreProperties(ignoreUnknown = true)
open class Image(type: JsonType = JsonType("image")) : BaseJsonModel(type) {

    companion object FieldNames {
        val TINY = "tiny"
        val SMALL = "small"
        val MEDIUM = "medium"
        val LARGE = "large"
        val ORIGINAL = "original"
    }

    var tiny: String? = null

    var small: String? = null

    var medium: String? = null

    var large: String? = null

    var original: String? = null

}