package app.youkai.data.service

import app.youkai.util.ext.append

class RequestBuilder<T>(val id: String, val call: (String, Map<String, String>, Map<String, String>) -> T) {

    private val INCLUDE: String = "include"
    private val FIELDS: String = "fields"
    private val FILTER: String = "filter"
    private val SORT: String = "sort"
    private val PAGE: String = "page"

    private val DELIMITER: String = ","

    private val headerMap = mutableMapOf<String, String>()
    private val queryMap = mutableMapOf<String, String>()

    fun get(): T {
        return call(id, headerMap, queryMap)
    }

    fun withHeader(header: String, value: String): RequestBuilder<T> {
        headerMap.put(header, value)
        return this
    }

    /**
     * Adds a query: include=relationship[1],relationship[2] ...
     * Related documentation: @see http://jsonapi.org/format/#fetching-includes
     * @param relationship  The names of top-level relationships.
     *                      Note: not the type of the relationship's returned object.
     */
    fun include(vararg relationship: String): RequestBuilder<T> {
        queryMap.append(INCLUDE, relationship.joinToString(DELIMITER), DELIMITER)
        return this
    }

    /**
     * Adds a query: include=relationship.nestedRelationship[1],relationship.nestedRelationship[2] ...
     * Related documentation: @see http://jsonapi.org/format/#fetching-includes
     * @param relationship  The name of the relationship top-level relationship.
     *                      Note: not the type of the relationship's returned object.
     * @param nestedRelationship The names of sub-relationships to [relationship]
     */
    fun includeNested(relationship: String, vararg nestedRelationship: String): RequestBuilder<T> {
        if (nestedRelationship.isEmpty())
            throw IllegalArgumentException("includeNested() should only be used when relationships of relationships are required.")
        nestedRelationship.forEach { queryMap.append(INCLUDE, "$relationship.$it", DELIMITER) }
        return this
    }

    fun fields(context: String, vararg queryParameter: String): RequestBuilder<T> {
        queryMap.append("$FIELDS[$context]", queryParameter.joinToString(DELIMITER), DELIMITER)
        return this
    }

    fun filter(context: String, vararg queryParameter: String): RequestBuilder<T> {
        queryMap.append("$FILTER[$context]", queryParameter.joinToString(DELIMITER), DELIMITER)
        return this
    }

    fun sort(queryParameter: String, descending: Boolean = false): RequestBuilder<T> {
        queryMap.append(SORT, if (descending) "-$queryParameter" else queryParameter, DELIMITER)
        return this
    }

    fun page(context: String, vararg queryParameter: Int): RequestBuilder<T> {
        queryMap.append("$PAGE[$context]", queryParameter.joinToString(DELIMITER), DELIMITER)
        return this
    }

}