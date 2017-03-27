package app.youkai.data.models

import com.github.jasminb.jsonapi.annotations.Type

@Type("streamers")
class Streamer : BaseJsonModel() {

    var siteName: String? = null

    var logo: String? = null

/**
 * This is left here for future generations, as streamers do not have streamingLinks implemented yet as relationships.
 *
    @Relationship("streamingLinks")
    var streamingLinks: List<StreamingLink>? = null

    @RelationshipLinks("streamingLinks")
    var streamingLinksLinks: Links? = null
 */

}