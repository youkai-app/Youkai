package app.youkai.data.service

import app.youkai.util.ext.append

class RequestBuilder<T>(val id: String, val call: (String, Map<String, String>) -> T) {

    val INCLUDE: String = "include"
    val FIELDS: String = "fields"
    val FILTER: String = "filter"
    val SORT: String = "sort"

    val DELIMITER: String = ","

    val queryMap = mutableMapOf<String, String>()

    fun get(): T {
        return call(id, queryMap)
    }

    fun include(vararg queryParameter: String): RequestBuilder<T> {
        queryMap.append(INCLUDE, queryParameter.joinToString(DELIMITER), DELIMITER)
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

}