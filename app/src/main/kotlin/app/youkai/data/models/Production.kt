package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*
import com.github.jasminb.jsonapi.annotations.Meta

@Type("animeProductions")
class Production : BaseJsonModel() {

    var role: String? = null

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("producer")
    var producer: Producer? = null

    @RelationshipLinks("producer")
    var producerLinks: com.github.jasminb.jsonapi.Links? = null

    @Meta
    var meta: CountMeta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

}



