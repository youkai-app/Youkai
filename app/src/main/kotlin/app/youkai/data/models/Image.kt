package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("image") @JsonIgnoreProperties(ignoreUnknown = true)
open class Image : BaseJsonModel() {

    companion object FieldNames {
        val TYPE = "image"
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