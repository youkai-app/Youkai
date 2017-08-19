package app.youkai.data.models.ext

import app.youkai.data.models.MediaRelationship
import app.youkai.util.string
import app.youkai.R

/**
 * Extensions for MediaRelationship
 */

val MediaRelationship.roleString
    get() = when (role) {
        "adaptation" -> string(R.string.media_relationship_role_adaptation)
        "alternative_setting" -> string(R.string.media_relationship_role_alternative_setting)
        "alternative_version" -> string(R.string.media_relationship_role_alternative_version)
        "character" -> string(R.string.media_relationship_role_character)
        "full_story" -> string(R.string.media_relationship_role_full_story)
        "other" -> string(R.string.media_relationship_role_other)
        "parent_story" -> string(R.string.media_relationship_role_parent_story)
        "prequel" -> string(R.string.media_relationship_role_prequel)
        "sequel" -> string(R.string.media_relationship_role_sequel)
        "side_story" -> string(R.string.media_relationship_role_side_story)
        "spinoff" -> string(R.string.media_relationship_role_spinoff)
        "summary" -> string(R.string.media_relationship_role_summary)
        else -> "?"
    }