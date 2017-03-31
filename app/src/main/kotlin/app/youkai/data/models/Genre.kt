package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("genres") @JsonIgnoreProperties(ignoreUnknown = true)
class Genre : BaseJsonModel() {

    var name: String? = null

    var slug: String? = null

    var description: String? = null

}