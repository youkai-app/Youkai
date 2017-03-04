package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*
import com.github.jasminb.jsonapi.annotations.Meta

@Type("mappings")
class Mapping : BaseJsonModel() {

    var externalSite: String? = null

    var externalId: String? = null

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

    @Meta
    var meta: CountMeta? = null

    @Links
    var links: com.github.jasminb.jsonapi.Links? = null

}



