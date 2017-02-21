package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("posterImage")
class PosterImage : BaseJsonModel() {

    var tiny: String? = null

    var small: String? = null

    var medium: String? = null

    var large: String? = null

    var original: String? = null

}