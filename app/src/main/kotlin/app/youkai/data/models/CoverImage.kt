package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("coverImage") @JsonIgnoreProperties(ignoreUnknown = true)
class CoverImage : BaseJsonModel() {

    var tiny: String? = null

    var small: String? = null

    var large: String? = null

    var original: String? = null

}