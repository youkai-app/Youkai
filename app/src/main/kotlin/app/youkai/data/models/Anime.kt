package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("anime") @JsonIgnoreProperties(ignoreUnknown = true)
class Anime : BaseMedia() {

    companion object FieldNames {
        val TYPE = "anime"
        val EPISODE_COUNT = "episodeCount"
        val EPISODE_LENGTH = "episodeLength"
        val SHOW_TYPE = "showType"
        val YOUTUBE_VIDEO_ID = "youtubeVideoId"
        val NSFW = "nsfw"
        val EPISODES = "episodes"
        val STREAMING_LINKS = "streamingLinks"
        val PRODUCTIONS = "animeProductions"
        val CHARACTERS = "animeCharacters"
        val STAFF = "animeStaff"
    }

    var episodeCount: Int? = null

    var episodeLength: Int? = null

    var showType: String? = null

    var youtubeVideoId: String? = null

    @JsonProperty("nsfw")
    var isNsfw: Boolean? = null

    @Relationship("episodes")
    var episodes: List<Episode>? = null

    @RelationshipLinks("episodes")
    var episodeLinks: Links? = null

    @Relationship("streamingLinks")
    var streamingLinks: List<StreamingLink>? = null

    @RelationshipLinks("streamingLinks")
    var streamingLinksLinks: Links? = null

    @Relationship("animeProductions")
    var productions: List<AnimeProduction>? = null

    @RelationshipLinks("animeProductions")
    var productionLinks: Links? = null

    @Relationship("animeCharacters")
    var animeCharacters: List<AnimeCharacter>? = null

    @RelationshipLinks("animeCharacters")
    var animeCharacterLinks: Links? = null

    @Relationship("animeStaff")
    var staff: List<AnimeStaff>? = null

    @RelationshipLinks("animeStaff")
    var staffLinks: Links? = null

}