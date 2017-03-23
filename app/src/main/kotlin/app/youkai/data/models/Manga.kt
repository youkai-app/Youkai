package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("manga")
class Manga : BaseMedia() {

    var chapterCount: Int? = null

    var volumeCount: Int? = null

    var serialization: String? = null

    var mangaType: String? = null

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