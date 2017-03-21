package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("characters")
class Character : BaseJsonModel() {

    var slug: String? = null

    var name: String? = null

    var malId: Int? = null

    var description: String? = null

    /*
     * Image is an object with a single field ("original") in the api.
     * This method prevents the need for creating a new object for this one property.
     * If the image object one day includes new properties (pretty much guaranteed to be strings)
     * this method should still work as expected.
    */
    var image: String? = null

    @JsonProperty("image")
    fun setImage(m: Map<String, String>) {
        image = m["original"];
    }

    @Relationship("castings")
    var casting: Casting? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

    /*
     * This is not implemented yet in the api, but is returned by character requests.
     * This is left here for future generations to discover, and lazily implement as required.
     *
    @Relationship("primaryMedia")
    var anime: Anime? = null

    @RelationshipLinks("primaryMedia")
    var animeLinks: Links? = null
     *
     */

}