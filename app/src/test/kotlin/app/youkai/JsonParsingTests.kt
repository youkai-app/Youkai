package app.youkai

import app.youkai.data.models.Anime
import app.youkai.data.models.Credentials
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.jasminb.jsonapi.ResourceConverter
import org.junit.Test

import org.junit.Assert.*

class JsonParsingTests {

    /*
    * Test local Jackson parsing for an example json object (slightly modified attack on Titan request response.
    */
    @Test
    @Throws(Exception::class)
    fun animeTest1 () {

        val resourceConverter = ResourceConverter(Anime::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("anime_json_response")
        val animeJsonDoc = resourceConverter.readDocument(testJson, Anime::class.java)
        val anime = animeJsonDoc.get()

        assertEquals("attack-on-titan", anime.slug)
        assertEquals("Centuries ago, manki...defeating them before the last walls are breached.\r\n[Written by MAL Rewrite]", anime.synopsis)
        assertEquals(263, anime.coverImageTopOffset)
        assertEquals("Attack on Titan", anime.titles?.en)
        assertEquals("Shingeki no Kyojin", anime.titles?.enJp)
        assertEquals("進撃の巨人", anime.titles?.jaJp)
        assertEquals("Shingeki no Kyojin", anime.canonicalTitle)
        assertEquals(4.2380021637554, anime.averageRating)
        anime.abbreviatedTitles?.forEach { title -> assertEquals("AoT", title) }
        assertEquals("141", anime.ratingFrequencies?.get("0.5")) //Don't check all because long.
        assertEquals("2521", anime.ratingFrequencies?.get("3.0"))
        assertEquals("13279", anime.ratingFrequencies?.get("5.0"))
        assertEquals(4017, anime.favoritesCount)
        assertEquals("2013-04-07", anime.startDate)
        assertEquals("2013-09-29", anime.endDate)
        assertEquals(2, anime.popularityRank)
        assertEquals(131, anime.ratingRank)
        assertEquals("R", anime.ageRating)
        assertEquals("17+ (violence & profanity)", anime.ageRatingGuide)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/tiny.jpg?1418580054", anime.posterImage?.tiny)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/small.jpg?1418580054", anime.posterImage?.small)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/medium.jpg?1418580054", anime.posterImage?.medium)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/large.jpg?1418580054", anime.posterImage?.large)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/original.jpg?1418580054", anime.posterImage?.original)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/tiny.jpg?1486065327", anime.coverImage?.tiny)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/small.jpg?1486065327", anime.coverImage?.small)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/large.jpg?1486065327", anime.coverImage?.large)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/original.png?1486065327", anime.coverImage?.original)
        assertEquals(25, anime.episodeCount)
        assertEquals(24, anime.episodeLength)
        assertEquals("TV" , anime.subtype)
        assertEquals("LHtdKWJdif4", anime.youtubeVideoId)
        assertEquals("TV", anime.showType)
        assertEquals(false, anime.nsfw)

    }

    @Test
    @Throws(Exception::class)
    fun credentialsTest () {
        val loginResponse = "{\"access_token\":\"46576873443657hg53667684m474hb34t34v232352543g64j64554y467edb551\",\"token_type\":\"bearer\",\"expires_in\":1373259,\"refresh_token\":\"fdsf34324325kl25l32k523n5235kkkkk42c02fca93966322222d03a4459df88\",\"scope\":\"public\",\"created_at\":1486761182}"

        val credentials = ObjectMapper().readValue(loginResponse, Credentials::class.java)

        assertEquals("46576873443657hg53667684m474hb34t34v232352543g64j64554y467edb551", credentials.accessToken)
        assertEquals("bearer", credentials.tokenType)
        assertEquals("1373259", credentials.expriesIn)
        assertEquals("fdsf34324325kl25l32k523n5235kkkkk42c02fca93966322222d03a4459df88", credentials.refreshToken)
        assertEquals("public", credentials.scope)
        assertEquals("1486761182", credentials.createdAt)

    }

}