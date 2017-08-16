package app.youkai.ui.common.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import app.youkai.R
import kotlinx.android.synthetic.main.activity_fragment_container.*

/**
 * A base Activity class that takes care of the dirty work of wrapper layout, fragment transaction,
 * and toolbar stuff...
 */
abstract class FragmentContainerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, getFragment())
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     * Returns the fragment to be used in this Activity
     */
    abstract fun getFragment(): Fragment
}