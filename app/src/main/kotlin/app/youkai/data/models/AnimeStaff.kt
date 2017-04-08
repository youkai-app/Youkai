package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("animeStaff") @JsonIgnoreProperties(ignoreUnknown = true)
class AnimeStaff : BaseJsonModel("animeStaff") {

    companion object FieldNames {
        val ROLE = "role"
        val ANIME = "anime"
        val PERSON = "person"
    }

    var role: String? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("person")
    var person: Producer? = null

    @RelationshipLinks("person")
    var personLinks: Links? = null

}

