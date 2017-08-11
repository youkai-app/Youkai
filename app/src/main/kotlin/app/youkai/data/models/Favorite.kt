package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("favorites") @JsonIgnoreProperties(ignoreUnknown = true)
class Favorite : BaseJsonModel(JsonType("favorites")) {

    companion object FieldNames {
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val FAV_RANK = "favRank"
        val USER = "user"
        val ITEM = "item"
    }

    var createdAt: String? = null

    var updatedAt: String? = null

    var favRank: Int? = null

    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("user")
    var userLinks: Links? = null

    @Relationship("item")
    var item: BaseJsonModel? = null

    @RelationshipLinks("item")
    var itemLinks: Links? = null

}