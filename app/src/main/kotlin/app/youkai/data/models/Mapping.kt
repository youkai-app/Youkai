package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*

@Type("mappings")
class Mapping : BaseJsonModel() {

    var externalSite: String? = null

    var externalId: String? = null

    @Relationship("media")
    var anime: Anime? = null

    @RelationshipLinks("media")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

}
