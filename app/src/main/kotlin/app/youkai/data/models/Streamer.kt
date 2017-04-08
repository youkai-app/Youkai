package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.annotations.Type

@Type("streamers") @JsonIgnoreProperties(ignoreUnknown = true)
class Streamer : BaseJsonModel("streamers") {

    companion object FieldNames {
        val SITE_NAME = "siteName"
        val LOGO = "logo"
        val STREAMING_LINKS = "streamingLinks"
    }

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