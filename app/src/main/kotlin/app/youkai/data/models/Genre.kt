package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Links
import com.github.jasminb.jsonapi.annotations.Meta
import com.github.jasminb.jsonapi.annotations.Type

@Type("genres")
class Genre : BaseJsonModel() {

    // TODO: enums for genres names

    var name: String? = null

    var slug: String? = null

    var description: String? = null

    @Meta
    var meta: CountMeta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

}