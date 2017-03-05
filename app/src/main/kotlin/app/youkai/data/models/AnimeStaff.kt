package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*

@Type("animeStaff")
class AnimeStaff : BaseJsonModel() {

    var role: String? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("person")
    var person: Producer? = null

    @RelationshipLinks("person")
    var personLinks: com.github.jasminb.jsonapi.Links? = null

}

