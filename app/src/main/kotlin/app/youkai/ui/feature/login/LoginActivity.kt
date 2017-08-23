package app.youkai.ui.feature.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.method.LinkMovementMethod
import android.view.View.GONE
import android.view.View.VISIBLE
import app.youkai.MainActivity
import app.youkai.R
import app.youkai.ui.feature.library_update.LibraryUpdateSheet
import app.youkai.util.ext.inputString
import app.youkai.util.ext.snackbar
import com.hannesdorfmann.mosby3.mvp.viewstate.MvpViewStateActivity
import com.jakewharton.rxbinding2.widget.TextViewAfterTextChangeEvent
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpViewStateActivity<LoginView, LoginPresenter, LoginState>(), LoginView {

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun createViewState() = LoginState()

    override fun onNewViewStateInstance() {
        /* do nothing */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        go.setOnClickListener { doLogin() }

        // Allows for HTML-formatted links in the text to be clickable.
        bottomLinks.movementMethod = LinkMovementMethod()

        val inputConsumer: Consumer<TextViewAfterTextChangeEvent> = Consumer({
            enableButton(
                    presenter.isUsernameSufficient(username.inputString())
                    && presenter.isPasswordSufficient(password.inputString())
            )
        })

        username.afterTextChangeEvents().subscribe(inputConsumer)
        password.afterTextChangeEvents().subscribe(inputConsumer)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        getState().usernameEnabled = username.isEnabled
        getState().passwordEnabled = password.isEnabled
        getState().buttonEnabled = go.isEnabled
        getState().progressVisible = progress.visibility == VISIBLE

        super.onSaveInstanceState(outState)
    }

    override fun enableUsername(enable: Boolean) {
        username.isEnabled = enable
    }

    override fun enablePassword(enable: Boolean) {
        password.isEnabled = enable
    }

    override fun enableButton(enable: Boolean) {
        go.isEnabled = enable
    }

    override fun showProgress(show: Boolean) {
        progress.visibility = if (show) VISIBLE else GONE
    }

    override fun showError(message: String) {
        val snackbar = root.snackbar(message) {}
        snackbar.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                getState().error = null
            }
        })
        getState().error = message
    }

    override fun setLoading(isLoading: Boolean) {
        getState().isLoading = isLoading
    }

    override fun doLogin() = presenter.doLogin(username.inputString(), password.inputString())

    override fun completeLogin() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getState(): LoginState = viewState
}