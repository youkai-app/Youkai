package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("mediaRelationships") @JsonIgnoreProperties(ignoreUnknown = true)
class MediaRelationship : BaseJsonModel(JsonType("mediaRelationships")) {

    companion object FieldNames {
        val ROLE = "role"
        val SOURCE = "source"
        val DESTINATION = "destination"
    }

    var role: String? = null

    @Relationship("source")
    var source: BaseMedia? = null

    @RelationshipLinks("source")
    var sourceLinks: Links? = null

    @Relationship("destination")
    var destination: BaseMedia? = null

    @RelationshipLinks("destination")
    var destinationLinks: Links? = null

}