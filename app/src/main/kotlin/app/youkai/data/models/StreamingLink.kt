package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("streamingLinks") @JsonIgnoreProperties(ignoreUnknown = true)
class StreamingLink : BaseJsonModel(JsonType("streamingLinks")) {

    companion object FieldNames {
        val URL = "url"
        val SUBS = "subs"
        val DUBS = "dubs"
        val STREAMER = "streamer"
        val MEDIA = "media"
    }

    var url: String? = null

    var subs: List<String>? = null

    var dubs: List<String>? = null

    @Relationship("streamer")
    var streamer: Streamer? = null

    @RelationshipLinks("streamer")
    var streamerLinks: Links? = null

    @Relationship("media")
    var media: BaseMedia? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

}