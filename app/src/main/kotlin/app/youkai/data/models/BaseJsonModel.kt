package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Id

/*
 * Each resource class must have an id field and it must be of type String (defined by the JSONAPI specification).
 */
open class BaseJsonModel {

    @Id
    private val id: String? = null

}