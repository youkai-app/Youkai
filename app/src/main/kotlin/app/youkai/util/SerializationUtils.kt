package app.youkai.util

object SerializationUtils {

    private val ID_MATCHING_REGEX_1 = "\"id\"\\s*:\\s*\""

    private val ID_MATCHING_REGEX_2 = "*\"\\s*,*"

    /**
     * This removes a specific ID field from a json source.
     * Currently the jsonapi library does not allow serialization of objects without IDs.
     * TODO: Submit a PR to allow for this use case.
     * @param json The json to remove the ID from
     * @param id The id of the fields that need to be removed. If there are multiple id fields with the same id supplied it will remove them all.
     */
    fun removeIdFromJson(json: String, id: String): String {
        return json.replace((ID_MATCHING_REGEX_1 + id + ID_MATCHING_REGEX_2).toRegex(), "")
    }

    /**
     * Removes the first id field from the json.
     * @param json The json to remove the id from
     */
    fun removeFirstIdFromJson(json: String): String {
        return json.replaceFirst((ID_MATCHING_REGEX_1 + "(\\w*\\d*\\s*)" + ID_MATCHING_REGEX_2).toRegex(), "")
    }

    /**
     * Removes all id fields from json.
     * @param json The json to remove the ids from
     */
    fun removeAllIdsFromJson(json: String): String {
        return json.replace((ID_MATCHING_REGEX_1 + "(\\w*\\d*\\s*)" + ID_MATCHING_REGEX_2).toRegex(), "")
    }

}