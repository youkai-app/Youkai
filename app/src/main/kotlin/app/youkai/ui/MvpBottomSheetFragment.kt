/**
 * TODO: update to mosby v3
 * Requires v3 of mosby and doesn't work anyway.
 *
package app.youkai.ui

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.view.View
import com.hannesdorfmann.mosby3.mvp.MvpPresenter
import com.hannesdorfmann.mosby3.mvp.MvpView
import com.hannesdorfmann.mosby3.mvp.delegate.FragmentMvpDelegateImpl
import com.hannesdorfmann.mosby3.mvp.delegate.MvpDelegateCallback

abstract class MvpBottomSheetFragment<V : MvpView, P : MvpPresenter<V>> : BottomSheetDialogFragment(), MvpDelegateCallback<V, P>, MvpView {

    protected val mvpDelegate: FragmentMvpDelegateImpl<V, P>
        get() = FragmentMvpDelegateImpl<V, P>(this, this, true, true)

    protected var mPresenter: P? = null

    override fun setPresenter(presenter: P) {
        mPresenter = presenter
    }

    override fun getPresenter(): P? = mPresenter

    override fun getMvpView(): V = this as V

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpDelegate.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mvpDelegate.onDestroyView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mvpDelegate.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpDelegate.onDestroy()
    }

    override fun onPause() {
        super.onPause()
        mvpDelegate.onPause()
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onResume()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onStop()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mvpDelegate.onActivityCreated(savedInstanceState)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        mvpDelegate.onAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        mvpDelegate.onDetach()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
    }
}
 **/