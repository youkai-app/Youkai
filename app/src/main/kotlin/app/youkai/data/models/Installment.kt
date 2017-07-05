package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("installments") @JsonIgnoreProperties(ignoreUnknown = true)
class Installment : BaseJsonModel(JsonType("installments")) {

    companion object FieldNames {
        val TAG = "tag"
        val POSITION = "position"
        val FRANCHISE = "franchise"
        val MEDIA = "media"
    }

    var tag: String? = null

    var position: Int? = null

    @Relationship("franchise")
    var franchise: Franchise? = null

    @RelationshipLinks("franchise")
    var franchiseLinks: Links? = null

    @Relationship("media")
    var anime: Anime? = null

    @Relationship("media")
    var manga: Manga? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

}