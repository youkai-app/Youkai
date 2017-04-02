package app.youkai.data.models.ext

import app.youkai.data.models.AnimeCharacter
import app.youkai.data.models.Character

/**
 * Media characters related extension functions
 */

/**
 * Returns a simple [Character] list
 */
fun Collection<AnimeCharacter>.toCharactersList(): List<Character?> {
    val characters = arrayListOf<Character?>()
    this.forEach {
        characters.add(it.character)
    }
    return characters
}
