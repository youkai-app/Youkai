package app.youkai.ui.feature.library_update.view

import app.youkai.ui.common.mvp.MediaTypesViewManager

interface LibraryUpdateViewManager: MediaTypesViewManager {

    override fun getViewT(): LibraryUpdateView?

    /**
     * TODO: put in [BaseLibraryUpdateView] once the [startActivtyForResult] stuff is pulled out of LibraryUpdateSheet
     */
    fun sendToLogin()

}