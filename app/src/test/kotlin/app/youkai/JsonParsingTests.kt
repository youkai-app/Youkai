package app.youkai

import app.youkai.data.models.*
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

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("anime_sparse_json")
        val animeJsonDoc = resourceConverter.readDocument(testJson, Anime::class.java)
        val anime = animeJsonDoc.get()

        assertEquals("7442", anime.id)
        assertEquals("https://kitsu.io/api/edge/anime/7442", anime.links!!.self!!.href) // remember to use ?? in production code

        assertEquals("attack-on-titan", anime.slug)
        assertEquals("Centuries ago, manki...defeating them before the last walls are breached.\r\n[Written by MAL Rewrite]", anime.synopsis)
        assertEquals(263, anime.coverImageTopOffset)
        assertEquals("Attack on Titan", anime.titles!!.en)
        assertEquals("Shingeki no Kyojin", anime.titles!!.enJp)
        assertEquals("進撃の巨人", anime.titles!!.jaJp)
        assertEquals("Shingeki no Kyojin", anime.canonicalTitle)
        assertEquals(4.23691079690624, anime.averageRating)
        anime.abbreviatedTitles?.forEach { title -> assertEquals("AoT", title) }
        assertEquals("141", anime.ratingFrequencies!!["0.5"]) //Don't check all ratingFreq's because lazy.
        assertEquals("2526", anime.ratingFrequencies!!["3.0"])
        assertEquals("13299", anime.ratingFrequencies!!["5.0"])
        assertEquals(4024, anime.favoritesCount)
        assertEquals("2013-04-07", anime.startDate)
        assertEquals("2013-09-29", anime.endDate)
        assertEquals(2, anime.popularityRank)
        assertEquals(132, anime.ratingRank)
        assertEquals("R", anime.ageRating)
        assertEquals("17+ (violence & profanity)", anime.ageRatingGuide)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/tiny.jpg?1418580054", anime.posterImage!!.tiny)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/small.jpg?1418580054", anime.posterImage!!.small)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/medium.jpg?1418580054", anime.posterImage!!.medium)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/large.jpg?1418580054", anime.posterImage!!.large)
        assertEquals("https://media.kitsu.io/anime/poster_images/7442/original.jpg?1418580054", anime.posterImage!!.original)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/tiny.jpg?1486065327", anime.coverImage!!.tiny)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/small.jpg?1486065327", anime.coverImage!!.small)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/large.jpg?1486065327", anime.coverImage!!.large)
        assertEquals("https://media.kitsu.io/anime/cover_images/7442/original.png?1486065327", anime.coverImage!!.original)
        assertEquals(25, anime.episodeCount)
        assertEquals(24, anime.episodeLength)
        assertEquals("TV" , anime.subtype)
        assertEquals("LHtdKWJdif4", anime.youtubeVideoId)
        assertEquals("TV", anime.showType)
        assertEquals(false, anime.isNsfw)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/genres", anime.genreLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/genres", anime.genreLinks!!.related.href)
        assertNull(anime.genres)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/castings", anime.castingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/castings", anime.castingLinks!!.related.href)
        assertNull(anime.castings)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/episodes", anime.episodeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/episodes", anime.episodeLinks!!.related.href)
        assertNull(anime.episodes)
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


    @Test
    @Throws(Exception::class)
    fun castingsTest () {
        val resourceConverter = ResourceConverter(Casting::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("castings_json")
        val castingsJsonDoc = resourceConverter.readDocumentCollection(testJson, Casting::class.java)  // notice different call (readDocumentCollection)
        val castings = castingsJsonDoc.get()

        val first = castings[0]!!

        assertEquals("121206", first.id)
        assertEquals("Director", first.role)
        assertEquals(false, first.isVoiceActor)
        assertEquals(false, first.featured)
        assertEquals(null, first.language)

        assertEquals("https://kitsu.io/api/edge/castings/121206/relationships/media", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/castings/121206/media", first.animeLinks!!.related.href)

        assertEquals(64, castingsJsonDoc.meta["count"])

        assertEquals("https://kitsu.io/api/edge/anime/7442/castings?page%5Blimit%5D=10&page%5Boffset%5D=10", castingsJsonDoc.links.next!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/castings?page%5Blimit%5D=10&page%5Boffset%5D=54", castingsJsonDoc.links.last!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/castings?page%5Blimit%5D=10&page%5Boffset%5D=0", castingsJsonDoc.links.first!!.href)
    }


    @Test
    @Throws(Exception::class)
    fun animeWithCastingsTest () {
        val resourceConverter = ResourceConverter(Anime::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("anime_with_castings_json")
        val animeJsonDoc = resourceConverter.readDocument(testJson, Anime::class.java)
        val anime = animeJsonDoc.get()

        assertEquals("https://kitsu.io/api/edge/anime/3936/relationships/castings", anime.castingLinks!!.self!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/3936/castings", anime.castingLinks!!.related!!.href)

        val first = anime.castings!!.first()

        assertEquals("86935", first.id)
        assertEquals("Script", first.role)
        assertEquals(false, first.isVoiceActor)
        assertEquals(false, first.featured)
        assertEquals(null, first.language)
        assertEquals("https://kitsu.io/api/edge/castings/86935", first.links!!.self!!.href)
    }

    @Test
    @Throws(Exception::class)
    fun genresTest () {
        val resourceConverter = ResourceConverter(Genre::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("genres_json")
        val genresJsonDoc = resourceConverter.readDocumentCollection(testJson, Genre::class.java)
        val genres = genresJsonDoc.get()

        val first = genres.first()

        assertEquals("2", first.id)
        assertEquals("Adventure", first.name)
        assertEquals("adventure", first.slug)
        assertEquals(null, first.description)
        assertEquals("https://kitsu.io/api/edge/genres/2", first.links!!.self.href)

        assertEquals(6, genresJsonDoc.meta["count"])
        assertEquals("https://kitsu.io/api/edge/anime/3936/genres?page%5Blimit%5D=10&page%5Boffset%5D=0", genresJsonDoc.links.first!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/3936/genres?page%5Blimit%5D=10&page%5Boffset%5D=0", genresJsonDoc.links.last!!.href)

        val second = genres[1]

        assertEquals("8", second.id)
        assertEquals("Magic", second.name)
        assertEquals("magic", second.slug)
        assertEquals("fake description that isn't empty... xiprox; DROP TABLE example_table; –#", second.description)
        assertEquals("https://kitsu.io/api/edge/genres/8", second.links!!.self.href)
    }


    @Test
    @Throws(Exception::class)
    fun animeWithGenresTest () {
        val resourceConverter = ResourceConverter(Anime::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("anime_with_genres_json")
        val animeJsonDoc = resourceConverter.readDocument(testJson, Anime::class.java)
        val anime = animeJsonDoc.get()

        assertEquals("https://kitsu.io/api/edge/anime/3936/relationships/genres", anime.genreLinks!!.self!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/3936/genres", anime.genreLinks!!.related!!.href)

        val first = anime.genres!!.first()

        assertEquals("2", first.id)
        assertEquals("Adventure", first.name)
        assertEquals("adventure", first.slug)
        assertEquals(null, first.description)
        assertEquals("https://kitsu.io/api/edge/genres/2", first.links!!.self.href)
    }

    /*
     * From here on, extra properties will not get their own animeWith... tests.
     * TODO: animWithEverythingTest()
     */

    @Test
    @Throws(Exception::class)
    fun episodesTest () {
        val resourceConverter = ResourceConverter(Episode::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("episodes_json")
        val episodesJsonDoc = resourceConverter.readDocumentCollection(testJson, Episode::class.java)
        val episodes = episodesJsonDoc.get()

        val first = episodes[0]!!

        assertEquals("66105", first.id)
        assertEquals("Bite of the Ant", first.titles!!.enJp)
        assertEquals(1, first.seasonNumber)
        assertEquals(43, first.number)
        assertEquals("something something Envy, Greed, spoilers, Rosé and Hohenheim.", first.synopsis)
        assertEquals("2010-02-07", first.airdate)
        assertEquals(null, first.length)
        assertEquals("https://media.kitsu.io/episodes/thumbnails/66105/original.jpg?1416321163", first.thumbnail)
        assertEquals("https://kitsu.io/api/edge/episodes/66105", first.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/episodes/66105/relationships/media", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/episodes/66105/media", first.animeLinks!!.related.href)

        assertEquals(64, episodesJsonDoc.meta["count"])

        assertEquals("https://kitsu.io/api/edge/anime/3936/episodes?page%5Blimit%5D=10&page%5Boffset%5D=10", episodesJsonDoc.links.next!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/3936/episodes?page%5Blimit%5D=10&page%5Boffset%5D=54", episodesJsonDoc.links.last!!.href)
        assertEquals("https://kitsu.io/api/edge/anime/3936/episodes?page%5Blimit%5D=10&page%5Boffset%5D=0", episodesJsonDoc.links.first!!.href)
    }

    @Test
    @Throws(Exception::class)
    fun MappingsTest () {
        val resourceConverter = ResourceConverter(Mapping::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("mappings_json")
        val mappingsJsonDoc = resourceConverter.readDocumentCollection(testJson, Mapping::class.java)
        val mappings = mappingsJsonDoc.get()

        val first = mappings[0]!!

        assertEquals("5686", first.id)
        assertEquals("myanimelist/anime", first.externalSite)
        assertEquals("16498", first.externalId)
        assertEquals("https://kitsu.io/api/edge/mappings/5686", first.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/mappings/5686/relationships/media", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/mappings/5686/media", first.animeLinks!!.related.href)

        assertEquals(3, mappingsJsonDoc.meta["count"])

        assertEquals("https://kitsu.io/api/edge/anime/7442/mappings?page%5Blimit%5D=10&page%5Boffset%5D=0", mappingsJsonDoc.links.first.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/mappings?page%5Blimit%5D=10&page%5Boffset%5D=0", mappingsJsonDoc.links.last.href)
    }



}