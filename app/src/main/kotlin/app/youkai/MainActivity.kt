package app.youkai

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
            startActivity(Intent(this, LoginActivity::class.java))
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
            startActivity(MediaActivity.new(this, id, type))
        }

        // Temporary for testing. TODO: Replace with real auth check.
        //startActivity(Intent(this, LoginActivity::class.java))

        //TODO: remove
        /**val testUpdateSheet = LibraryUpdateSheet();
        val bundle = Bundle()
        bundle.putString(LibraryUpdateSheet.ARGUMENT_ANIME_ID, "1")
        bundle.putString(LibraryUpdateSheet.ARGUMENT_LIBRARY_ENTRY_ID, "17370281")
        testUpdateSheet.arguments = bundle
        testUpdateSheet.show(supportFragmentManager, testUpdateSheet.tag) **/
    }

}
