package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("categories") @JsonIgnoreProperties(ignoreUnknown = true)
class Category : BaseJsonModel(JsonType("categories")) {

    companion object FieldNames {
        val CREATED_AT = "createdAt"
        val UPDATED_AT = "updatedAt"
        val TITLE = "title"
        val DESCRIPTION = "description"
        val TOTAL_MEDIA_COUNT = "totalMediaCount"
        val SLUG = "slug"
        val NSFW = "nsfw"
        val CHILD_COUNT = "childCount"
        val IMAGE = "image"
    }

    var createdAt: String? = null

    var updatedAt: String? = null

    var title: String? = null

    var description: String? = null

    var totalMediaCount: Int? = null

    var slug: String? = null

    var nsfw: Boolean? = null

    var childCount: Int? = null

    /**
     * I'm guessing this is of type [Image] since I couldn't find any with it actually set.
     */
    var image: Image? = null

    @Relationship("parent")
    var parent: Category? = null

    @RelationshipLinks("parent")
    var parentLinks: Links? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("manga")
    var manga: Manga? = null

    @RelationshipLinks("manga")
    var mangaLinks: Links? = null

    /**
    @Relationship("drama")
    var drama: Drama? = null

    @RelationshipLinks("drama")
    var dramaLinks: Links? = null
    */

}