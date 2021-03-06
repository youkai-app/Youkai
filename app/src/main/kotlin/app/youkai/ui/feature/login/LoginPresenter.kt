package app.youkai.ui.feature.login

import app.youkai.data.local.Credentials
import app.youkai.data.service.Api
import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : MvpBasePresenter<LoginView>() {

    val subscriptions: CompositeDisposable = CompositeDisposable()

    override fun attachView(view: LoginView?) {
        super.attachView(view)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
        if (!retainInstance) subscriptions.dispose()
    }

    fun doLogin(username: String, password: String) {
        view?.enableUsername(false)
        view?.enablePassword(false)
        view?.enableButton(false)
        view?.showProgress()
        view?.setLoading(true)

        val disposable = Api.login(username.trim(), password) // Usernames may not have spaces, passwords may have spaces.
                .subscribeOn(Schedulers.io())
                .flatMap {
                    loginResponse ->
                    Credentials.username = username
                    Credentials.authToken = loginResponse.accessToken
                    Credentials.refreshToken = loginResponse.refreshToken
                    Api.allUsersAuth(loginResponse.accessToken ?: "")
                            .filter("self", "true").get()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            Credentials.userId = it.get()?.get(0)?.id
                        },
                        {
                            onError(it)
                        },
                        { // onComplete
                            view?.showProgress(false)
                            view?.setLoading(false)
                            view?.completeLogin()
                        }
                )

        subscriptions.add(disposable)
    }

    // Usernames may not be empty, nor contain spaces.
    fun isUsernameSufficient(username: String): Boolean = username.isNotBlank()

    // Passwords may not be blank, but may contain spaces.
    fun isPasswordSufficient(password: String): Boolean = password.isNotEmpty()

    private fun onError(e: Throwable?) {
        view?.enableUsername()
        view?.enablePassword()
        view?.enableButton()
        view?.showProgress(false)
        view?.setLoading(false)
        view?.showError(e?.message ?: "An error occurred.")
    }
}