package app.youkai

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

class App : Application() {

    override fun onCreate() {
        context = this
        super.onCreate()
        Fresco.initialize(this)
    }

    companion object {
        /**
         * A static [Context] accessible from everywhere.
         */
        lateinit var context: Context
    }
}