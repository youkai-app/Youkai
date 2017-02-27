package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Id
import com.github.jasminb.jsonapi.annotations.Links
import com.github.jasminb.jsonapi.annotations.Meta

/*
 * Each resource class must have an id field and it must be of type String (defined by the JSONAPI specification).
 */
open class BaseJsonModel {

    @Id
    var id: String? = null

/*
 * Does not currently work as expected.
 * Have commented out until we can get it sorted.
 * TODO: Fix. Test written in JsonParsingTest.kt (also commented out).

    @Meta
    var meta: app.youkai.data.models.Meta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

*/

}