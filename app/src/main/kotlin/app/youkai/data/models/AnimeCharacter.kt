package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.*

@Type("animeCharacters")
class AnimeCharacter : BaseJsonModel() {

    var role: String? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("character")
    var character: Character? = null

    @RelationshipLinks("character")
    var characterLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("castings")
    var casting: Casting? = null

    @RelationshipLinks("castings")
    var castingLinks: com.github.jasminb.jsonapi.Links? = null

}