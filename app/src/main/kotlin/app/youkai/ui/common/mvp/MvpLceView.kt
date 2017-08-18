package app.youkai.ui.common.mvp

import android.support.annotation.DrawableRes
import com.hannesdorfmann.mosby.mvp.MvpView

interface MvpLceView : MvpView {
    fun switchToLoading()
    fun switchToContent()
    fun switchToError()
    fun setError(title: String, subtitle: String, @DrawableRes imageRes: Int? = null, showRetryButton: Boolean = false)
}