package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.annotations.*

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

    //TODO: relationships: installments, reviews, mediaRelationships

    @Relationship("genres")
    var genres: List<Genre>? = null

    @RelationshipLinks("genres")
    var genreLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("castings")
    var castings: List<Casting>? = null

    @RelationshipLinks("castings")
    var castingLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("episodes")
    var episodes: List<Episode>? = null

    @RelationshipLinks("episodes")
    var episodeLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("mappings")
    var mappings: List<Mapping>? = null

    @RelationshipLinks("mappings")
    var mappingLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("animeProductions")
    var productions: List<AnimeProduction>? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("animeCharacters")
    var animeCharacters: List<AnimeCharacter>? = null

    @RelationshipLinks("animeCharacters")
    var animeCharacterLinks: com.github.jasminb.jsonapi.Links? = null

    @Relationship("animeStaff")
    var animeStaff: List<AnimeStaff>? = null

    @RelationshipLinks("animeStaff")
    var animeStaffLinks: com.github.jasminb.jsonapi.Links? = null

}