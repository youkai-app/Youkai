package app.youkai.data.models

import com.github.jasminb.jsonapi.ResourceConverter

//TODO: change package
object ResourceConverters {

    /**
     * Don't have to list all the classes manually, but we're doing so to prevent errors due to missing them.
     * Hopefully some diligence will keep this list updated.
     */
    val mainConverter by lazy {
        ResourceConverter(
                Anime::class.java,
                AnimeCharacter::class.java,
                AnimeProduction::class.java,
                AnimeStaff::class.java,
                Casting::class.java,
                Character::class.java,
                Episode::class.java,
                Favorite::class.java,
                Franchise::class.java,
                Genre::class.java,
                Installment::class.java,
                LibraryEntry::class.java,
                Manga::class.java,
                Mapping::class.java,
                MediaRelationship::class.java,
                Person::class.java,
                Producer::class.java,
                Review::class.java,
                Streamer::class.java,
                StreamingLink::class.java,
                User::class.java)
    }

}