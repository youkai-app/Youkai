package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("animeCharacters") @JsonIgnoreProperties(ignoreUnknown = true)
class AnimeCharacter : BaseJsonModel("animeCharacters") {

    companion object FieldNames {
        val ROLE = "role"
        val ANIME = "anime"
        val CHARACTER = "character"
        val CASTINGS = "castings"
    }

    var role: String? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("character")
    var character: Character? = null

    @RelationshipLinks("character")
    var characterLinks: Links? = null

    @Relationship("castings")
    var casting: Casting? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

}