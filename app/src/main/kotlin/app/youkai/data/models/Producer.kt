package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("producers") @JsonIgnoreProperties(ignoreUnknown = true)
class Producer : BaseJsonModel(JsonType("producers")) {

    companion object FieldNames {
        val SLUG = "slug"
        val NAME = "name"
        val PRODUCTIONS = "animeProductions"
    }

    var slug: String? = null

    var name: String? = null

    @Relationship("animeProductions")
    var productions: Anime? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: Links? = null

}