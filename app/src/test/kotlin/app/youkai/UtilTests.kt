package app.youkai

import app.youkai.util.ext.capitalizeFirstLetter
import org.junit.Test
import kotlin.test.assertEquals

class UtilTests {

    @Test
    @Throws(Exception::class)
    fun capitalizeFirstLetterTest() {
        val testString = "abcd"
        assertEquals(testString.capitalizeFirstLetter(), "Abcd")
    }

}