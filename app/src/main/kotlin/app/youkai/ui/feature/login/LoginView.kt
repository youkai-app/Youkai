package app.youkai.ui.feature.login

import com.hannesdorfmann.mosby.mvp.MvpView

interface LoginView : MvpView {
    /**
     * Enables or disables the username input field
     */
    fun enableUsername(enable: Boolean = true)

    /**
     * Enables or disables the password input field
     */
    fun enablePassword(enable: Boolean = true)

    /**
     * Enables or disables the login button
     */
    fun enableButton(enable: Boolean = true)

    /**
     * Shows or hides the loading progress bar
     */
    fun showProgress(show: Boolean = true)

    /**
     * Shows a Snackbar message with the give String
     */
    fun showError(message: String)

    /**
     * Finishes itself (LoginActivity) and starts MainActivity
     */
    fun completeLogin()
}