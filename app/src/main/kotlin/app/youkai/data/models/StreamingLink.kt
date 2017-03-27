package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("streamingLinks")
class StreamingLink : BaseJsonModel() {

    var url: String? = null

    var subs: List<String>? = null

    var dubs: List<String>? = null

    @Relationship("streamer")
    var streamer: Streamer? = null

    @RelationshipLinks("streamer")
    var streamerLinks: Links? = null

    @Relationship("media")
    var media: Media? = null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

}