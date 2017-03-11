package app.youkai.data.service

class JsonApiRequestBuilder<T> (val id: String, val call: (String, Map<String, String>) -> T) {

    val queryMap = mutableMapOf<String, String>()

    fun get(): T {
        return call(id, queryMap)
    }

    fun include(queryParameter: String): JsonApiRequestBuilder<T> {
        queryMap.put("include", queryParameter)
        return this
    }
    /*
    fun filter(queryParameter: String): JsonApiRequestBuilder<T> {
        queryMap.put("include", queryParameter)
        return this
    }

    fun fields(queryParameter: String): JsonApiRequestBuilder<T> {
        queryMap.put("include", queryParameter)
        return this
    }*/

}