package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("genres")
class Genre : BaseJsonModel() {

    var name: String? = null

    var slug: String? = null

    var description: String? = null

}