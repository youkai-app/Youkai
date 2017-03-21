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
    fun animeSparseTest () {
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

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/mappings", anime.mappingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/mappings", anime.mappingLinks!!.related.href)
        assertNull(anime.mappings)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/anime-productions", anime.productionLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-productions", anime.productionLinks!!.related.href)
        assertNull(anime.productions)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/anime-characters", anime.animeCharacterLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-characters", anime.animeCharacterLinks!!.related.href)
        assertNull(anime.animeCharacters)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/anime-staff", anime.animeStaffLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-staff", anime.animeStaffLinks!!.related.href)
        assertNull(anime.animeStaff)

        assertEquals("https://kitsu.io/api/edge/anime/7442/relationships/installments", anime.installmentLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/installments", anime.installmentLinks!!.related.href)
        assertNull(anime.installments)
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
    fun mappingsTest () {
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
        assertNull(first.anime)

        assertEquals(3, mappingsJsonDoc.meta["count"])

        assertEquals("https://kitsu.io/api/edge/anime/7442/mappings?page%5Blimit%5D=10&page%5Boffset%5D=0", mappingsJsonDoc.links.first.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/mappings?page%5Blimit%5D=10&page%5Boffset%5D=0", mappingsJsonDoc.links.last.href)
    }

    @Test
    @Throws(Exception::class)
    fun animeCharactersTest () {
        val resourceConverter = ResourceConverter(AnimeCharacter::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("characters_anime_json")
        val animeCharactersJsonDoc = resourceConverter.readDocumentCollection(testJson, AnimeCharacter::class.java)
        val animeCharacters = animeCharactersJsonDoc.get()

        val first = animeCharacters[0]!!

        assertEquals("7508", first.id)
        assertEquals("main", first.role)
        assertEquals("https://kitsu.io/api/edge/anime-characters/7508", first.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/relationships/anime", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/anime", first.animeLinks!!.related.href)
        assertNull(first.anime)

        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/relationships/character", first.characterLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/character", first.characterLinks!!.related.href)
        assertNull(first.character)

        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/relationships/castings", first.castingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-characters/7508/castings", first.castingLinks!!.related.href)
        assertNull(first.casting)

        assertEquals(72, animeCharactersJsonDoc.meta["count"])

        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-characters?page%5Blimit%5D=10&page%5Boffset%5D=0", animeCharactersJsonDoc.links.first.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-characters?page%5Blimit%5D=10&page%5Boffset%5D=10", animeCharactersJsonDoc.links.next.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-characters?page%5Blimit%5D=10&page%5Boffset%5D=62", animeCharactersJsonDoc.links.last.href)
    }

    @Test
    @Throws(Exception::class)
    fun characterTest () {
        val resourceConverter = ResourceConverter(Character::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("character_json")
        val characterJsonDoc = resourceConverter.readDocument(testJson, Character::class.java)
        val character = characterJsonDoc.get()

        assertEquals("38505", character.id)
        assertEquals("mikasa-ackerman", character.slug)
        assertEquals("Mikasa Ackerman", character.name)
        assertEquals(40881, character.malId)
        assertEquals("Age: 15<br/>BirthHeight: 17 metres! WOW! WHAT A TALL GIRL...are inseparable.</span>", character.description)
        assertEquals("https://media.kitsu.io/characters/images/38505/original.jpg?1483096805", character.image)
        assertEquals("https://kitsu.io/api/edge/characters/38505", character.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/characters/38505/relationships/castings", character.castingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/characters/38505/castings", character.castingLinks!!.related.href)
        assertNull(character.casting)
    }

    @Test
    @Throws(Exception::class)
    fun animeProductionsTest () {
        val resourceConverter = ResourceConverter(AnimeProduction::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("productions_anime_json")
        val animeProductionsJsonDoc = resourceConverter.readDocumentCollection(testJson, AnimeProduction::class.java)
        val animeProductions = animeProductionsJsonDoc.get()

        val first = animeProductions[0]

        assertEquals("18636", first.id)
        assertEquals("producer", first.role)
        assertEquals("https://kitsu.io/api/edge/anime-productions/18636", first.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/anime-productions/18636/relationships/anime", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-productions/18636/anime", first.animeLinks!!.related.href)
        assertNull(first.anime)

        assertEquals("https://kitsu.io/api/edge/anime-productions/18636/relationships/producer", first.producerLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-productions/18636/producer", first.producerLinks!!.related.href)
        assertNull(first.producer)

        assertEquals(9, animeProductionsJsonDoc.meta["count"])
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-productions?page%5Blimit%5D=10&page%5Boffset%5D=0", animeProductionsJsonDoc.links.first.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-productions?page%5Blimit%5D=10&page%5Boffset%5D=0", animeProductionsJsonDoc.links.last.href)
    }

    @Test
    @Throws(Exception::class)
    fun producerTest () {
        val resourceConverter = ResourceConverter(Producer::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("producer_json")
        val producerJsonDoc = resourceConverter.readDocument(testJson, Producer::class.java)
        val producer = producerJsonDoc.get()

        assertEquals("532", producer.id)
        assertEquals("mad-box", producer.slug)
        assertEquals("Mad Box", producer.name)
        assertEquals("https://kitsu.io/api/edge/producers/532", producer.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/producers/532/relationships/anime-productions", producer.productionLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/producers/532/anime-productions", producer.productionLinks!!.related.href)
        assertNull(producer.productions)
    }

    @Test
    @Throws(Exception::class)
    fun animeStaffTest () {
        val resourceConverter = ResourceConverter(AnimeStaff::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("staff_anime_json")
        val animeStaffJsonDoc = resourceConverter.readDocumentCollection(testJson, AnimeStaff::class.java)
        val animeStaff = animeStaffJsonDoc.get()

        val first = animeStaff[0]

        assertEquals("5526", first.id)
        assertEquals("Producer", first.role)
        assertEquals("https://kitsu.io/api/edge/anime-staff/5526", first.links!!.self.href)

        assertEquals("https://kitsu.io/api/edge/anime-staff/5526/relationships/anime", first.animeLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-staff/5526/anime", first.animeLinks!!.related.href)
        assertNull(first.anime)

        assertEquals("https://kitsu.io/api/edge/anime-staff/5526/relationships/person", first.personLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/anime-staff/5526/person", first.personLinks!!.related.href)
        assertNull(first.person)

        assertEquals(58, animeStaffJsonDoc.meta["count"])
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-staff?page%5Blimit%5D=10&page%5Boffset%5D=0", animeStaffJsonDoc.links.first.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-staff?page%5Blimit%5D=10&page%5Boffset%5D=10", animeStaffJsonDoc.links.next.href)
        assertEquals("https://kitsu.io/api/edge/anime/7442/anime-staff?page%5Blimit%5D=10&page%5Boffset%5D=48", animeStaffJsonDoc.links.last.href)
    }

    @Test
    @Throws(Exception::class)
    fun installmentsTest () {
        val resourceConverter = ResourceConverter(Installment::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("installments_json")
        val installmentsJsonDoc = resourceConverter.readDocumentCollection(testJson, Installment::class.java)
        val installments = installmentsJsonDoc.get()

        val first = installments[0]

        assertEquals("1139", first.id)
        assertEquals(null, first.tag)
        assertEquals(0, first.position)

        assertEquals("https://kitsu.io/api/edge/installments/1139/relationships/franchise", first.franchiseLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/installments/1139/franchise", first.franchiseLinks!!.related.href)
        assertNull(first.franchise)

        assertEquals("https://kitsu.io/api/edge/installments/1139/relationships/media", first.mediaLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/installments/1139/media", first.mediaLinks!!.related.href)
        assertNull(first.media)
    }

    @Test
    @Throws(Exception::class)
    fun franchiseTest () {
        val resourceConverter = ResourceConverter(Installment::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("franchise_json")
        val franchiseJsonDoc = resourceConverter.readDocument(testJson, Franchise::class.java)
        val franchise = franchiseJsonDoc.get()

        assertEquals("400", franchise.id)
        assertEquals("JoJo's Bizarre Adventure", franchise.titles!!.en)
        assertEquals("", franchise.titles!!.enJp)
        assertEquals("", franchise.canonicalTitle)

        assertEquals("https://kitsu.io/api/edge/franchises/400/relationships/installments", franchise.installmentLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/franchises/400/installments", franchise.installmentLinks!!.related.href)
        assertNull(franchise.installments)
    }


    @Test
    @Throws(Exception::class)
    fun mangaSparseTest () {
        val resourceConverter = ResourceConverter(Manga::class.java)

        val testJson = ClassLoader.getSystemClassLoader().getResourceAsStream("manga_sparse_json")
        val mangaJsonDoc = resourceConverter.readDocument(testJson, Manga::class.java)
        val manga = mangaJsonDoc.get()

        assertEquals("66", manga.id)
        assertEquals("https://kitsu.io/api/edge/manga/66", manga.links!!.self!!.href) // remember to use ?? in production code

        assertEquals("fullmetal-alchemist", manga.slug)
        assertEquals("...to restore their dwakof...", manga.synopsis)
        assertEquals(0, manga.coverImageTopOffset)
        assertEquals(null, manga.titles!!.en)
        assertEquals("Fullmetal Alchemist", manga.titles!!.enJp)
        assertEquals(null, manga.titles!!.jaJp)
        assertEquals("Fullmetal Alchemist", manga.canonicalTitle)
        assertEquals(null, manga.abbreviatedTitles)
        assertEquals(4.59892638812736, manga.averageRating)
        assertEquals("2", manga.ratingFrequencies!!["0.5"]) //Don't check all ratingFreq's because lazy.
        assertEquals("24", manga.ratingFrequencies!!["3.0"])
        assertEquals("813", manga.ratingFrequencies!!["5.0"])
        assertEquals(469, manga.favoritesCount)
        assertEquals("2001-07-12", manga.startDate)
        assertEquals("2010-06-11", manga.endDate)
        assertEquals(13, manga.popularityRank)
        assertEquals(2, manga.ratingRank)
        assertEquals(null, manga.ageRating)
        assertEquals(null, manga.ageRatingGuide)
        assertEquals("https://media.kitsu.io/manga/poster_images/66/tiny.jpg?1434249530", manga.posterImage!!.tiny)
        assertEquals("https://media.kitsu.io/manga/poster_images/66/small.jpg?1434249530", manga.posterImage!!.small)
        assertEquals("https://media.kitsu.io/manga/poster_images/66/medium.jpg?1434249530", manga.posterImage!!.medium)
        assertEquals("https://media.kitsu.io/manga/poster_images/66/large.jpg?1434249530", manga.posterImage!!.large)
        assertEquals("https://media.kitsu.io/manga/poster_images/66/original.gif?1434249530", manga.posterImage!!.original)
        assertEquals("https://media.kitsu.io/manga/cover_images/66/tiny.jpg?1430838562", manga.coverImage!!.tiny)
        assertEquals("https://media.kitsu.io/manga/cover_images/66/small.jpg?1430838562", manga.coverImage!!.small)
        assertEquals("https://media.kitsu.io/manga/cover_images/66/large.jpg?1430838562", manga.coverImage!!.large)
        assertEquals("https://media.kitsu.io/manga/cover_images/66/original.jpg?1430838562", manga.coverImage!!.original)
        assertEquals(109, manga.chapterCount)
        assertEquals(27, manga.volumeCount)
        assertEquals("manga" , manga.subtype)
        assertEquals("Shounen Gangan", manga.serialization)
        assertEquals("manga", manga.mangaType)

        assertEquals("https://kitsu.io/api/edge/manga/66/relationships/genres", manga.genreLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/manga/66/genres", manga.genreLinks!!.related.href)
        assertNull(manga.genres)

        assertEquals("https://kitsu.io/api/edge/manga/66/relationships/castings", manga.castingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/manga/66/castings", manga.castingLinks!!.related.href)
        assertNull(manga.castings)

        assertEquals("https://kitsu.io/api/edge/manga/66/relationships/installments", manga.installmentLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/manga/66/installments", manga.installmentLinks!!.related.href)
        assertNull(manga.installments)

        assertEquals("https://kitsu.io/api/edge/manga/66/relationships/mappings", manga.mappingLinks!!.self.href)
        assertEquals("https://kitsu.io/api/edge/manga/66/mappings", manga.mappingLinks!!.related.href)
        assertNull(manga.mappings)
    }

}