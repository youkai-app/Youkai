package app.youkai.ui.feature.settings

import android.os.Bundle
import com.hannesdorfmann.mosby.mvp.viewstate.RestorableViewState

class SettingsState : RestorableViewState<SettingsView> {

    override fun apply(view: SettingsView?, retained: Boolean) {

    }

    override fun restoreInstanceState(`in`: Bundle?): RestorableViewState<SettingsView> {
        return SettingsState()
    }

    override fun saveInstanceState(out: Bundle) {

    }

}
