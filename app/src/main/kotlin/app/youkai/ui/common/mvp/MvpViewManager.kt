package app.youkai.ui.common.mvp

import com.hannesdorfmann.mosby.mvp.MvpView

interface MvpViewManager : MvpView {

    fun getViewT(): MvpView?

}