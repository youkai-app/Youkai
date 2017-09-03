package app.youkai.data.local

import android.content.Context
import android.content.SharedPreferences
import app.youkai.App
import app.youkai.util.ext.unitify

/**
 * A SharedPreferences manager object that takes care of storing user credentials.
 */
object Credentials {
    private val NAME = "credentials_storage"

    private val KEY_USERNAME = "username"
    private val KEY_AUTH_TOKEN = "auth_token"
    private val KEY_REFRESH_TOKEN = "refresh_token"
    private val KEY_USER_ID = "user_id"

    private var prefs: SharedPreferences

    init {
        prefs = App.context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var username: String?
        set(value) = prefs.edit().putString(KEY_USERNAME, value).apply().unitify()
        get() = prefs.getString(KEY_USERNAME, null)

    var authToken: String?
        set(value) = prefs.edit().putString(KEY_AUTH_TOKEN, value).apply().unitify()
        get() = prefs.getString(KEY_AUTH_TOKEN, null)

    var refreshToken: String?
        set(value) = prefs.edit().putString(KEY_REFRESH_TOKEN, value).apply().unitify()
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)

    var userId: String?
        set(value) = prefs.edit().putString(KEY_USER_ID, value).apply()
        get() = prefs.getString(KEY_USER_ID, null)

    val isAuthenticated: Boolean
        get() = username != null && authToken != null
}