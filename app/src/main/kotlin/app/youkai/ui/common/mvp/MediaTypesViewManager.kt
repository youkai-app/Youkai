package app.youkai.ui.common.mvp

import app.youkai.data.models.ext.MediaType

interface MediaTypesViewManager : MvpViewManager {

    fun setMediaType(mediaType: MediaType)

}