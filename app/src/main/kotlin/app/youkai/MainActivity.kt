package app.youkai

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.youkai.data.local.Credentials
import app.youkai.data.models.ext.MediaType
import app.youkai.ui.feature.login.LoginActivity
import app.youkai.ui.feature.media.MediaActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Credentials.isAuthenticated) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        // SNK Manga = 14916
        // SNK Anime = 7442
        // Bakemonogatari = 3919
        startActivity(MediaActivity.new(this, "14916", MediaType.MANGA, null, null))
    }
}
