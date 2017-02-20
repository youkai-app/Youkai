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
    private val KEY_PASSWORD = "password"
    private val KEY_AUTH_TOKEN = "auth_token"
    private val KEY_REFRESH_TOKEN = "refresh_token"

    private var prefs: SharedPreferences

    init {
        prefs = App.context.getSharedPreferences(NAME, Context.MODE_PRIVATE)
    }

    var username: String?
        set(value) = prefs.edit().putString(KEY_USERNAME, value).commit().unitify()
        get() = prefs.getString(KEY_USERNAME, null)

    var password: String?
        set(value) = prefs.edit().putString(KEY_PASSWORD, value).commit().unitify()
        get() = prefs.getString(KEY_PASSWORD, null)

    var authToken: String?
        set(value) = prefs.edit().putString(KEY_AUTH_TOKEN, value).commit().unitify()
        get() = prefs.getString(KEY_AUTH_TOKEN, null)

    var refreshToken: String?
        set(value) = prefs.edit().putString(KEY_REFRESH_TOKEN, value).commit().unitify()
        get() = prefs.getString(KEY_REFRESH_TOKEN, null)

    val isAuthenticated: Boolean
        get() = username != null && password != null && authToken != null
}