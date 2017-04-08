package app.youkai.data.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.github.jasminb.jsonapi.Links
import com.github.jasminb.jsonapi.annotations.Relationship
import com.github.jasminb.jsonapi.annotations.RelationshipLinks
import com.github.jasminb.jsonapi.annotations.Type

@Type("franchises") @JsonIgnoreProperties(ignoreUnknown = true)
class Franchise: BaseJsonModel("franchises") {

    companion object FieldNames {
        val TITLES = "titles"
        val CANONICAL_TITLE = "canonicalTitle"
        val INSTALLMENTS = "installments"
    }

    var titles: Titles? = null

    var canonicalTitle: String? = null

    @Relationship("installments")
    var installments: List<Installment>? = null

    @RelationshipLinks("installments")
    var installmentLinks: Links? = null

}