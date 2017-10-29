package app.youkai.data.local

import android.content.Context
import android.content.SharedPreferences
import app.youkai.App
import app.youkai.util.ext.unitify

/**
 * A SharedPreferences manager object that takes care of storing user credentials.
 */
object Credentials {
    private const val NAME = "credentials_storage"

    private const val KEY_USERNAME = "username"
    private const val KEY_AUTH_TOKEN = "auth_token"
    private const val KEY_REFRESH_TOKEN = "refresh_token"
    private const val KEY_USER_ID = "user_id"

    private val prefs: SharedPreferences

    init {
        prefs = App.context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var username: String?
        set(value) = prefs.edit().putString(KEY_USERNAME, value).commit().unitify()
        get() = prefs.getString(KEY_USERNAME, null)

    var authToken: String?
        set(value) = prefs.edit().putString(KEY_AUTH_TOKEN, value).commit().unitify()
        get() = prefs.getString(KEY_AUTH_TOKEN, null)

    var refreshToken: String?
        set(value) = prefs.edit().putString(KEY_REFRESH_TOKEN, value).commit().unitify()
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)

    var userId: String?
        set(value) = prefs.edit().putString(KEY_USER_ID, value).apply()
        get() = prefs.getString(KEY_USER_ID, null)

    val isAuthenticated: Boolean
        get() = username != null && authToken != null

    fun logout() {
        prefs.edit().clear().apply()
    }

}
