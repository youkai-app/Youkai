package app.youkai.ui.feature.login

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class LoginPresenter : MvpBasePresenter<LoginView>() {

    override fun attachView(view: LoginView?) {
        super.attachView(view)
    }

    override fun detachView(retainInstance: Boolean) {
        super.detachView(retainInstance)
    }

    // Part of mock. TODO: Remove
    var tryTimes = 0

    fun doLogin(username: String, password: String) {
        if (!validateInput(username, password)) return

        view?.enableUsername(false)
        view?.enablePassword(false)
        view?.enableButton(false)
        view?.showProgress()

        // Some lame mock... Will be replaced with actual API call.
        // TODO: Don't forget to handle state changes for the API call.
        Observable.create<Boolean> {
            Thread.sleep(3000)
            if (tryTimes++ % 2 == 0) {
                it.onError(null)
            } else {
                it.onNext(true)
            }
        }
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Boolean> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: Boolean?) {
                        view?.showProgress(false)
                    }

                    override fun onError(t: Throwable?) {
                        view?.enableUsername()
                        view?.enablePassword()
                        view?.enableButton()
                        view?.showProgress(false)
                        view?.showError("No API. Please try again.")
                    }

                    override fun onComplete() {
                    }

                })
    }

    /**
     * Checks for the validity of inputs and displays errors in the View if necessary
     */
    private fun validateInput(username: String, password: String): Boolean {
        var usernamePass = true
        var passwordPass = true

        if (username.trim().isEmpty()) {
            view?.showUsernameRequired()
            usernamePass = false
        } else {
            view?.showUsernameRequired(false)
        }

        if (password.trim().isEmpty()) {
            view?.showPasswordRequired()
            passwordPass = false
        } else {
            view?.showPasswordRequired(false)
        }

        return usernamePass && passwordPass
    }
}