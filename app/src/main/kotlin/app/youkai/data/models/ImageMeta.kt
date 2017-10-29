package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("meta") @JsonIgnoreProperties(ignoreUnknown = true)
open class ImageMeta(type: JsonType = JsonType("meta")) : BaseJsonModel(type) {

    companion object FieldNames {
        val TINY = "tiny"
        val SMALL = "small"
        val MEDIUM = "medium"
        val LARGE = "large"
    }

    /**
     * arrays of width, height
     */

    var tiny: Array<String>? = null

    var small: Array<String>? = null

    var medium: Array<String>? = null

    var large: Array<String>? = null

    var original: Array<String>? = null
}