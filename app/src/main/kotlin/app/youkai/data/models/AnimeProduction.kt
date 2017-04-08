package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("animeProductions") @JsonIgnoreProperties(ignoreUnknown = true)
class AnimeProduction : BaseJsonModel("animeProductions") {

    companion object FieldNames {
        val ROLE = "role"
        val ANIME = "anime"
        val PRODUCER = "producer"
    }

    var role: String? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("producer")
    var producer: Producer? = null

    @RelationshipLinks("producer")
    var producerLinks: Links? = null

}

