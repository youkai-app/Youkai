package app.youkai

import app.youkai.util.SerializationUtils
import app.youkai.util.ext.capitalizeFirstLetter
import org.junit.Test
import kotlin.test.assertEquals

class UtilTests {

    @Test
    fun capitalizeFirstLetterTest() {
        val testString = "abcd"
        assertEquals(testString.capitalizeFirstLetter(), "Abcd")
    }

    @Test
    fun removeIdFromJsonTest() {
        val testString = "{ \"id\":\"temporaryID\" }"
        assertEquals("{ }", SerializationUtils.removeIdFromJson(testString, "temporaryID"))
    }

    @Test
    fun removeIdFromJsonMultipleTest() {
        val testString = "{ \"id\":\"temporaryID\" \"id\":\"REAL ID\" \"id\":\"temporaryID\" }"
        assertEquals("{ \"id\":\"REAL ID\" }", SerializationUtils.removeIdFromJson(testString, "temporaryID"))
    }

    @Test
    fun removeFirstIdFromJsonTest() {
        val testString = "{ \"id\":\"temporaryID\" \"id\":\"REAL ID\" \"id\":\"temporaryID\" }"
        assertEquals("{ \"id\":\"REAL ID\" \"id\":\"temporaryID\" }", SerializationUtils.removeFirstIdFromJson(testString))
    }

    @Test
    fun removeAllIdsFromJsonTest() {
        val testString = "{ \"id\":\"temporaryID\" \"id\":\"REAL ID\" \"id\":\"temporaryID\" }"
        assertEquals("{ }", SerializationUtils.removeAllIdsFromJson(testString))
    }

}