package app.youkai.data.models

import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("people")
class Person : BaseJsonModel() {

    var name: String? = null

    var malId: Int? = null

    @Relationship("castings")
    var castings: List<Casting>? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

}