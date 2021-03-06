package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("characters") @JsonIgnoreProperties(ignoreUnknown = true)
class Character : BaseJsonModel(JsonType("characters")) {

    companion object FieldNames {
        val SLUG = "slug"
        val NAME = "name"
        val MAL_ID = "malId"
        val DESCRIPTION = "description"
        val IMAGE = "image"
        val CASTINGS = "castings"
    }

    var slug: String? = null

    var name: String? = null

    var malId: Int? = null

    var description: String? = null

    var image: Image? = null

    @Relationship("castings")
    var casting: Casting? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

    /*
     * This is not implemented yet in the api, but is returned by character requests.
     * This is left here for future generations to discover, and lazily implement as required.
     *
    @Relationship("primaryMedia")
    var media: BaseMedia? = null

    @RelationshipLinks("primaryMedia")
    var mediaLinks: Links? = null
    */

}