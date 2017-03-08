package app.youkai.data.models

import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("franchises")
class Franchise: BaseJsonModel() {

    var titles: Titles? = null

    var canonicalTitle: String? = null

    @Relationship("installments")
    var installments: List<Installment>? = null

    @RelationshipLinks("installments")
    var installmentLinks: Links? = null

}