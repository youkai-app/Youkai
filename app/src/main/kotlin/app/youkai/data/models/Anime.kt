package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("anime") @JsonIgnoreProperties(ignoreUnknown = true) //TODO: REMOVE @JsonIgnoreProperties
class Anime : BaseJsonModel() {

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

    var episodeCount: Int? = null

    var subtype: String? = null

    var episodeLength: Int? = null

    var showType: String? = null

    var youtubeVideoId: String? = null

    @JsonProperty("nsfw")
    var isNsfw: Boolean? = null

    @Relationship("genres")
    var genres: List<Genre>? = null

    @RelationshipLinks("genres")
    var genreLinks: Links? = null

    @Relationship("castings")
    var castings: List<Casting>? = null

    @RelationshipLinks("castings")
    var castingLinks: Links? = null

    @Relationship("episodes")
    var episodes: List<Episode>? = null

    @RelationshipLinks("episodes")
    var episodeLinks: Links? = null

    @Relationship("mappings")
    var mappings: List<Mapping>? = null

    @RelationshipLinks("mappings")
    var mappingLinks: Links? = null

    @Relationship("animeProductions")
    var productions: List<AnimeProduction>? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: Links? = null

    @Relationship("animeCharacters")
    var animeCharacters: List<AnimeCharacter>? = null

    @RelationshipLinks("animeCharacters")
    var animeCharacterLinks: Links? = null

    @Relationship("animeStaff")
    var animeStaff: List<AnimeStaff>? = null

    @RelationshipLinks("animeStaff")
    var animeStaffLinks: Links? = null

    //TODO: relationships: reviews, mediaRelationships

    @Relationship("installments")
    var installments: List<Installment>? = null

    @RelationshipLinks("installments")
    var installmentLinks: Links? = null

}