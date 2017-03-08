package app.youkai.data.models

import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("installments")
class Installment : BaseJsonModel() {

    var tag: String? = null

    var position: Int? = null

    @Relationship("franchise")
    var franchise: Franchise? = null

    @RelationshipLinks("franchise")
    var franchiseLinks: Links? = null

    @Relationship("media")
    var media: Anime?= null

    @RelationshipLinks("media")
    var mediaLinks: Links? = null

}