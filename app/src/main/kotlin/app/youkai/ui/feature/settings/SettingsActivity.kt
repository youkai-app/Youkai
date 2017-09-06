package app.youkai.ui.feature.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import app.youkai.BuildConfig
import app.youkai.R
import app.youkai.data.local.Credentials
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : MvpViewStateActivity<SettingsView, SettingsPresenter>(), SettingsView {

    override fun createPresenter() = SettingsPresenter()

    override fun createViewState() = SettingsState()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        checkPreferenceView.setOnClickListener {
            checkPreferenceView.value = checkPreferenceView.value != true
        }

        switchPreferenceView.setOnClickListener {
            switchPreferenceView.value = switchPreferenceView.value != true
        }

        logout.description = getString(R.string.you_are_logged_in_as_x, Credentials.username)
        logout.setOnClickListener {

        }

        versionInfo.description = resources.getString(R.string.version_information_format,
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE)
    }

    override fun onNewViewStateInstance() {

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, SettingsActivity::class.java)
    }

}
