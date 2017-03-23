package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks

@JsonIgnoreProperties(ignoreUnknown = true)
open class BaseMedia : BaseJsonModel() {

    var slug: String? = null

    var synopsis: String? = null

    var coverImageTopOffset: Int? = null

    var titles: Titles? = null

    var canonicalTitle: String? = null

    var abbreviatedTitles: List<String>? = null

    var averageRating: Double? = null

    var ratingFrequencies: Map<String, String>? = null

    var favoritesCount: Int? = null

    var startDate: String? = null

    var endDate: String? = null

    var popularityRank: Int? = null

    var ratingRank: Int? = null

    var ageRating: String? = null

    var ageRatingGuide: String? = null

    var posterImage: PosterImage? = null

    var coverImage: CoverImage? = null

    var subtype: String? = null

    @Relationship("genres")
    var genres: List<Genre>? = null

    @RelationshipLinks("genres")
    var genreLinks: Links? = null

    @Relationship("castings")
    var castings: List<Casting>? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

    @Relationship("installments")
    var installments: List<Installment>? = null

    @RelationshipLinks("installments")
    var installmentLinks: Links? = null

    @Relationship("mappings")
    var mappings: List<Mapping>? = null

    @RelationshipLinks("mappings")
    var mappingLinks: Links? = null

    @Relationship("mediaRelationships")
    var medias: List<Media>? = null

    @RelationshipLinks("mediaRelationships")
    var mediaLinks: Links? = null

    //TODO: relationships: reviews

}