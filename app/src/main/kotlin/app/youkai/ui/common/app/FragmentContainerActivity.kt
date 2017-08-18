package app.youkai.ui.common.app

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import app.youkai.R
import com.hannesdorfmann.mosby.mvp.MvpActivity
import com.hannesdorfmann.mosby.mvp.MvpPresenter
import com.hannesdorfmann.mosby.mvp.MvpView
import kotlinx.android.synthetic.main.activity_fragment_container.*

/**
 * A base Activity class that takes care of the dirty work of wrapper layout, fragment transaction,
 * and toolbar stuff...
 */
abstract class FragmentContainerActivity<V : MvpView, P : MvpPresenter<V>> : MvpActivity<V, P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_container)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, newFragment())
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    protected fun getToolbar(): Toolbar = toolbar

    /**
     * Returns the fragment to be used in this Activity
     */
    abstract fun newFragment(): Fragment
}