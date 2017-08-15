package app.youkai

import android.app.Application
import android.content.Context
<<<<<<< HEAD
import com.facebook.drawee.backends.pipeline.Fresco
=======
import app.youkai.ui.feature.library_update.AnimeStatusResolver
>>>>>>> 84d2275... Add status picking

class App : Application() {

    override fun onCreate() {
        context = this
        super.onCreate()
<<<<<<< HEAD
        Fresco.initialize(this)
=======
        AnimeStatusResolver.init(this)
>>>>>>> 84d2275... Add status picking
    }

    companion object {
        /**
         * A static [Context] accessible from everywhere.
         */
        lateinit var context: Context
    }
}