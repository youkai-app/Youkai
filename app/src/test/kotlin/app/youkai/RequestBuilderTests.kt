package app.youkai

import app.youkai.data.service.RequestBuilder
import org.junit.Test
import kotlin.test.assertEquals

class RequestBuilderTests {

    private val testQueriesCall = { _: String, _: Map<String, String>, queries: Map<String, String> -> queries }

    @Test
    fun includeNestedTest() {
        RequestBuilder("", testQueriesCall)
                .include("1234", "5678")
                .includeNested("first", "second", "third")
                .get()
                .forEach { _, query -> assertEquals("1234,5678,first.second,first.third", query) }
    }

}