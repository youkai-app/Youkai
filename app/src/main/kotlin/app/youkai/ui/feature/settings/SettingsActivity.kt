package app.youkai.ui.feature.settings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import app.youkai.BuildConfig
import app.youkai.MainActivity
import app.youkai.R
import app.youkai.data.local.Credentials
import com.hannesdorfmann.mosby.mvp.viewstate.MvpViewStateActivity
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : MvpViewStateActivity<SettingsView, SettingsPresenter>(), SettingsView {

    override fun createPresenter() = SettingsPresenter()

    override fun createViewState() = SettingsState()

    private fun logout() {
        AlertDialog.Builder(this)
                .setMessage(R.string.logout_confirmation)
                .setNegativeButton(R.string.cancel, null)
                .setPositiveButton(R.string.ok, { dialog, which ->
                    Credentials.logout()
                    startActivity(Intent.makeRestartActivityTask(
                            MainActivity.getLaunchIntent(this).component))
                    finish()
                })
                .show()
    }

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
            logout()
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
