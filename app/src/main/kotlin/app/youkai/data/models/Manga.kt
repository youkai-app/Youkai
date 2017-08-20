package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("manga") @JsonIgnoreProperties(ignoreUnknown = true)
class Manga : BaseMedia(JsonType("manga")) {

    companion object FieldNames {
        val CHAPTERS_COUNT = "chaptersCount"
        val VOLUME_COUNT = "volumeCount"
        val SERIALIZATION = "serialization"
        val MANGA_TYPE = "mangaType"
        val MANGA_CHAPTERS = "chapters"
        val CHARACTERS = "mangaCharacters"
        val STAFF = "mangaStaff"
    }

    var chapterCount: Int? = null

    var volumeCount: Int? = null

    var serialization: String? = null

    var mangaType: String? = null

    @Relationship("chapters")
    var chapters: List<Chapter>? = null

    @RelationshipLinks("chapters")
    var chaptersLinks: Links? = null

    /*
     * These relationships exist in Kitsu's manga model but are not hooked up.
     * This is left here in the hope that future generations may appreciate @dulleh's hard work.
     *
    @Relationship("mangaCharacters")
    var mangaCharacters: List<MangaCharacter>? = null

    @RelationshipLinks("mangaCharacters")
    var mangaCharactersLinks: Links? = null

    @Relationship("mangaStaff")
    var mangaStaff: List<MangaStaff>? = null

    @RelationshipLinks("mangaStaff")
    var mangaStaffLinks: Links? = null
    */

}