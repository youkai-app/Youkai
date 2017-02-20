package app.youkai.ui.feature.login

import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState

class LoginState : RestorableViewState<LoginView> {
    var error: String? = null
    var usernameEnabled = true
    var passwordEnabled = true
    var buttonEnabled = true
    var progressVisible = false

    override fun apply(view: LoginView, retained: Boolean) {
        if (error != null) {
            view.showError(error!!)
        }
        view.enableUsername(usernameEnabled)
        view.enablePassword(passwordEnabled)
        view.enableButton(buttonEnabled)
        view.showProgress(progressVisible)
    }

    override fun restoreInstanceState(bundle: Bundle?): RestorableViewState<LoginView> {
        val state = LoginState()
        state.error = bundle?.getString(KEY_ERROR)
        state.usernameEnabled = bundle?.getBoolean(KEY_USERNAME_ENABLED) ?: false
        state.passwordEnabled = bundle?.getBoolean(KEY_PASSWORD_ENABLED) ?: false
        state.buttonEnabled = bundle?.getBoolean(KEY_BUTTON_ENABLED) ?: false
        state.progressVisible = bundle?.getBoolean(KEY_PROGRESS_VISIBLE) ?: false
        return state
    }

    override fun saveInstanceState(bundle: Bundle) {
        bundle.putString(KEY_ERROR, error)
        bundle.putBoolean(KEY_USERNAME_ENABLED, usernameEnabled)
        bundle.putBoolean(KEY_PASSWORD_ENABLED, passwordEnabled)
        bundle.putBoolean(KEY_BUTTON_ENABLED, buttonEnabled)
        bundle.putBoolean(KEY_PROGRESS_VISIBLE, progressVisible)
    }

    companion object {
        private val KEY_ERROR = "error"
        private val KEY_USERNAME_ENABLED = "username_enabled"
        private val KEY_PASSWORD_ENABLED = "password_enabled"
        private val KEY_BUTTON_ENABLED = "button_enabled"
        private val KEY_PROGRESS_VISIBLE = "progress_visible"
    }
}