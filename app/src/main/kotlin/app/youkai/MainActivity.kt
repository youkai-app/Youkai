package app.youkai

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.youkai.ui.feature.login.LoginActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Temporary for testing. TODO: Replace with real auth check.
        startActivity(Intent(this, LoginActivity::class.java))
    }
}
