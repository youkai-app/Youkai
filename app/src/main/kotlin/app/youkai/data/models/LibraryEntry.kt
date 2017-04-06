package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type


@Type("libraryEntries") //@JsonIgnoreProperties(ignoreUnknown = true) TODO: uncomment
class LibraryEntry : BaseJsonModel() {

    var status: String? = null

    var progress: Int? = null

    var reconsuming: Boolean? = null

    var reconsumeCount: Int? = null

    var notes: String? = null

    var private: Boolean? = null

    var updatedAt: String? = null

    //TODO: remove and never ever use
    var rating: String? = null

    var ratingTwenty: Int? = null

    @Relationship("user")
    var user: User? = null

    @RelationshipLinks("user")
    var userLinks: Links? = null

    @Relationship("anime")
    var anime: Anime? = null

    @RelationshipLinks("anime")
    var animeLinks: Links? = null

    @Relationship("manga")
    var manga: Manga? = null

    @RelationshipLinks("manga")
    var mangaLinks: Links? = null

    //TODO: confirm if implemented
    @Relationship("review")
    var review: Review? = null

    @RelationshipLinks("review")
    var reviewLinks: Links? = null

    //TODO: Fix for polymorphism
    @Relationship("media")
    var media: Media? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

/*
    Are returned by the API but aren't implemented.
    //TODO: check if actually used
    @Relationship("unit")
    var unit: IDEK? = null

    @RelationshipLinks("unit")
    var unitLinks: Links? = null

    @Relationship("nextUnit")
    var nextUnit: IDEK? = null

    @RelationshipLinks("nextUnit")
    var nextUnitLinks: IDEK? = null

    @Relationship("drama")
    var drama: Drama? = null

    @RelationshipLinks("drama")
    var dramaLinks: Links? = null
*/

}