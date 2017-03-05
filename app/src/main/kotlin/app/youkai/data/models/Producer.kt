package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*

@Type("producers")
class Producer : BaseJsonModel() {

    var slug: String? = null

    var name: String? = null

    @Relationship("animeProductions")
    var productions: Anime? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: com.github.jasminb.jsonapi.Links? = null

}