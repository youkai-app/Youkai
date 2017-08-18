package app.youkai.ui.common.mvp

import android.support.annotation.DrawableRes
import android.widget.ViewFlipper
import app.youkai.util.ext.setDisplayedChildSafe
import com.hannesdorfmann.mosby.mvp.MvpFragment
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import tr.xip.errorview.ErrorView

/**
 * An MvpFragment subclass which takes care of loading, content, error sates in the background
 */
abstract class MvpLceFragment<V : MvpLceView, P : MvpPresenter<V>> : MvpFragment<V, P>(), MvpLceView, ErrorView.RetryListener {
    private val FLIPPER_LOADING = 0
    private val FLIPPER_CONTENT = 1
    private val FLIPPER_ERROR = 2

    protected abstract fun getViewFlipper(): ViewFlipper
    protected abstract fun getErrorView(): ErrorView

    override fun switchToLoading() {
        getViewFlipper().setDisplayedChildSafe(FLIPPER_LOADING)
    }

    override fun switchToContent() {
        getViewFlipper().setDisplayedChildSafe(FLIPPER_CONTENT)
    }

    override fun switchToError() {
        getViewFlipper().setDisplayedChildSafe(FLIPPER_ERROR)
    }

    override fun setError(title: String, subtitle: String, @DrawableRes imageRes: Int?, showRetryButton: Boolean) {
        val ev = getErrorView().setTitle(title).setSubtitle(subtitle).setRetryVisible(showRetryButton)
        if (imageRes != null) ev.setImage(imageRes)
        if (showRetryButton) ev.setRetryListener(this)
    }

    override fun onRetry() {
        /* implement in subclass */
    }
}