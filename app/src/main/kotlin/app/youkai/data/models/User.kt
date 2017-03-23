package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

/**
 * This is a dummy class for users. The rest of the fields will come in ~time~.
 */
@Type("user")
class User : BaseJsonModel() {

    var name: String? = null

    var avatar: Image? = null

}
