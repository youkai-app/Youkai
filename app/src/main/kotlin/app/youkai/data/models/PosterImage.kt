package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("posterImage")
class PosterImage : Image() {

    companion object FieldNames {
        val TYPE = "posterImage"
    }

}