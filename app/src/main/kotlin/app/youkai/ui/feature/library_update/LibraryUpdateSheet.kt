package app.youkai.ui.feature.library_update

import android.content.Context
import android.support.design.widget.BottomSheetDialog
import app.youkai.R

class LibraryUpdateSheet(context: Context) : BottomSheetDialog(context, R.style.MaterialDialogSheet) { //TODO: Change theme

    init {
        setContentView(R.layout.library_update)
        setCanceledOnTouchOutside(true)
        hide()
        show()
    }

}