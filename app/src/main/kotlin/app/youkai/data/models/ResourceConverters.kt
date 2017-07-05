package app.youkai.data.models

import com.github.jasminb.jsonapi.DeserializationFeature
import com.github.jasminb.jsonapi.ResourceConverter
import com.github.jasminb.jsonapi.SerializationFeature

object ResourceConverters {

    val libraryEntryConverter by lazy {
        val rc = ResourceConverter(LibraryEntry::class.java)
        rc.disableDeserializationOption(DeserializationFeature.REQUIRE_RESOURCE_ID)
        return@lazy rc
    }

    val animeConverter by lazy { ResourceConverter(Anime::class.java) }

    val mediaConverter: ResourceConverter by lazy { ResourceConverter(Media::class.java) }

}