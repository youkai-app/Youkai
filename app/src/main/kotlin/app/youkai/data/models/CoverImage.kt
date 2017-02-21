package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("coverImage")
class CoverImage : BaseJsonModel() {

    var tiny: String? = null

    var small: String? = null

    var large: String? = null

    var original: String? = null

}