package app.youkai

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import app.youkai.data.local.Credentials
import app.youkai.ui.feature.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!Credentials.isAuthenticated) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
