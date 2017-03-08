package app.youkai.data.models

import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("producers")
class Producer : BaseJsonModel() {

    var slug: String? = null

    var name: String? = null

    @Relationship("animeProductions")
    var productions: Anime? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: Links? = null

}