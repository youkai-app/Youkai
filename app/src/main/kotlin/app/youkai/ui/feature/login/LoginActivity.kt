package app.youkai.ui.feature.login

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.method.LinkMovementMethod
import android.view.View.GONE
import android.view.View.VISIBLE
import app.youkai.MainActivity
import app.youkai.R
import app.youkai.util.ext.snackbar
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import com.hannesdorfmann.mosby.mvp.viewstate.ViewState
import com.jakewharton.rxbinding2.widget.afterTextChangeEvents
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpViewStateActivity<LoginView, LoginPresenter>(), LoginView {

    override fun createPresenter(): LoginPresenter = LoginPresenter()

    override fun createViewState(): ViewState<LoginView> = LoginState()

    override fun onNewViewStateInstance() {
        /* do nothing */
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        go.setOnClickListener {
            presenter.doLogin(username.text.toString(), password.text.toString())
        }

        bottomLinks.movementMethod = LinkMovementMethod()

        username.afterTextChangeEvents()
                .subscribe {
                    presenter.updateLoginButtonWithInputFields(username.text.toString(), password.text.toString())
        }

        password.afterTextChangeEvents()
                .subscribe {
                    presenter.updateLoginButtonWithInputFields(username.text.toString(), password.text.toString())
        }
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

    override fun completeLogin() {
        finish()
        startActivity(Intent(this, MainActivity::class.java))
    }

    private fun getState(): LoginState = viewState as LoginState
}