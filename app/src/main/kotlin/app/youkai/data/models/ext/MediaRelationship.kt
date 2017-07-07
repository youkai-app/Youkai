package app.youkai.data.models.ext

import app.youkai.data.models.MediaRelationship

/**
 * Media relationship related extensions
 */

val MediaRelationship.source
    get() = if (sourceAnime != null) sourceAnime else if (sourceManga != null) sourceManga else null

val MediaRelationship.destination
    get() = if (destinationAnime != null) {
        destinationAnime
    } else if (destinationManga != null) {
        destinationManga
    } else null
