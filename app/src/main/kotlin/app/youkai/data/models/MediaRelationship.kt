package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("mediaRelationships") @JsonIgnoreProperties(ignoreUnknown = true)
class MediaRelationship : BaseJsonModel() {

    /*
    * Whenever a type is needed it will have to be inferred from the role and from
    * what type of object the MediaRelationship was returned by. I.E. a "sequel" for a manga
    * will be a Manga object, but a "sequel" for an anime will be an Anime object.
    */

    var role: String? = null

    @Relationship("source")
    var source: Media? = null

    @RelationshipLinks("source")
    var sourceLinks: Links? = null

    @Relationship("destination")
    var destination: Media? = null

    @RelationshipLinks("destination")
    var destinationLinks: Links? = null

}