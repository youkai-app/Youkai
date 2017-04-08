package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("people") @JsonIgnoreProperties(ignoreUnknown = true)
class Person : BaseJsonModel(JsonType("people")) {

    companion object FieldNames {
        val IMAGE = "image"
        val NAME = "name"
        val MAL_ID = "malId"
        val CASTINGS = "castings"
    }

    var image: String? = null

    var name: String? = null

    var malId: Int? = null

    @Relationship("castings")
    var castings: List<Casting>? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

}