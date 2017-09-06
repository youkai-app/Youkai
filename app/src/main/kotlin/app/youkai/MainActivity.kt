package app.youkai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.youkai.data.local.Credentials
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.feature.login.LoginActivity
import app.youkai.ui.feature.media.MediaActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

}
