package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*

@Type("animeProductions")
class Producer : BaseJsonModel() {

    var slug: String? = null

    var name: String? = null

    @Relationship("animeProductions")
    var anime: Anime? = null

    @RelationshipLinks("animeProductions")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

}



