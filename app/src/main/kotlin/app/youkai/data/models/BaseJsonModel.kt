package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Id
import com.github.jasminb.jsonapi.annotations.Links

open class BaseJsonModel {

    /*
     * Each resource class must have an id field and it must be of type String (defined by the JSONAPI specification).
     */
    @Id
    var id: String? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

}