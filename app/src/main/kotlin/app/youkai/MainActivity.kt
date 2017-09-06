package app.youkai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import app.youkai.data.local.Credentials
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.feature.login.LoginActivity
import app.youkai.ui.feature.media.MediaActivity
import app.youkai.ui.feature.settings.SettingsActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar as Toolbar)

        if (!Credentials.isAuthenticated) {
            startActivity(LoginActivity.getLaunchIntent(this))
            finish()
        }

        // SNK Manga = 14916
        // SNK Anime = 7442
        // Bakemonogatari = 3919
        // Hanamonogatari = 8032
        // Development code. TODO: Remove
        go.setOnClickListener {
            val id = mediaId.text.toString()
            val type = MediaType.fromString(mediaType.text.toString())
            startActivity(MediaActivity.getLaunchIntent(this, id, type))
        }
    }

    companion object {
        fun getLaunchIntent(context: Context) = Intent(context, MainActivity::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.activity_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.settings -> {
            startActivity(SettingsActivity.getLaunchIntent(this))
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

}
