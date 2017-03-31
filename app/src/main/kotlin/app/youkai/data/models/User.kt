package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

/**
 * This is a dummy class for users. The rest of the fields will come in ~time~.
 */
@Type("users") @JsonIgnoreProperties(ignoreUnknown = true)
class User : BaseJsonModel() {

    var name: String? = null

    var avatar: Image? = null

}
