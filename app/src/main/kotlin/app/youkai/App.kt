package app.youkai

import android.app.Application
import android.content.Context

class App : Application() {

    override fun onCreate() {
        context = this
        super.onCreate()
    }

    companion object {
        /**
         * A static [Context] accessible from everywhere.
         */
        lateinit var context: Context
    }
}